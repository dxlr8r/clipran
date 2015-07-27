(ns clipran.core
  (:require
   [clj-pid.core :as pid]
   [clojure.tools.cli :refer [parse-opts]]
   [clojure.set :refer [difference]]
   [clojure.string :refer [join split replace] :rename {replace str-replace}]
   [clipran.sh :refer [sh]]
   [clipran.tools :refer [cprintln]]
   [clipran.ip-tools :refer [ip? address-or-range? range->ips]])
  (:import [sun.misc Signal SignalHandler])
  (:gen-class))

(def cli-options
  [
   ["-c" "--cmd COMMAND" "command %RANGE% %IP%"
    :validate[#(not (empty? %)) "Command cannot be empty"]
    ]
   ["-r" "--range RANGE" "IP address or range"
    :validate[address-or-range? "Not an IP address/range"]
    ]
   ["-i" "--ip IP" "IP address"
     :validate[ip? "Not an IP address"]
    ]
   [
    "-b" "--background" "Run cmds in background, kill cmds by sending ctrl-c or if demonized SIGUSR2 to ppid. ppid found in /tmp/ip-map-pid.pid"
    ]
   [
    "-h" "--help" "Show help"
    ]
   ])


(defn- req-opts [result & required]
  (let [errors (:errors result)
        options (:options result)
        summary (:summary result)
        help? (:help options)

        no-args? (and (empty? options) (not (empty? (list required))))
        errors? (not (empty? errors))


        differences (map name (difference (set required) (set (keys options)))),
        reformat #(str
                   (re-find (re-pattern (str #"-.,\s+" "--" % #_#".+")) summary)
                   ": required option missing")
        missing (vec (map #(reformat %) differences))

        missing-req-opts? (not (empty? differences))
        ]
    (cond
     help? (vector summary)
     no-args? (vector "You must specify atleast one valid argument" summary)
     errors? errors
     missing-req-opts? missing
     :else nil
     )))

(defn- generate-commands [options]
  (let [ip-list (range->ips (:range options))
        opt-ip (:ip options)
        opt-cmd (:cmd options)]
    (for [ip ip-list,
          :let [cmd (str-replace opt-cmd #"%RANGE%|%IP%" {"%RANGE%" ip "%IP%" opt-ip})
                args (split cmd #"\s+" )]]
      cmd)))

(defn- run-commands [cmds background?]
  (for [cmd cmds]
    (do
      (if background?
        (future (sh cmd))
        (sh cmd))
      cmd)))


(defn- exit [] (System/exit 0))

(defn- pid-list []
  (sh (join " " (list "pgrep" "-P" (pid/current)))))

(defn- kill [pid-list]
  (doseq [pid pid-list]
    (sh (join " " (list "kill" pid)))))

(defn- init-signal []
  (Signal/handle (Signal. "USR2")
                 (reify SignalHandler
                   (handle [_ _]
                     (do
                       (kill (pid-list))
                       (exit))))))

(defn -main
  "Entry point"
  [& args]
  (let [result (parse-opts args cli-options)
        options (:options result)
        errors? (req-opts result :cmd :ip :range)
        background? (:background options)
        pid-file (str "/tmp/ip-map-" (pid/current) ".pid")]
    (do
      (if background? (do
                        (pid/save pid-file)
                        (pid/delete-on-shutdown! pid-file)
                        (init-signal)) nil)
      (if errors?
        (cprintln errors?)
        (cprintln (run-commands (generate-commands options) background?))))))

;(-main "-c" "echo touch %RANGE% %IP%" "-r" "10.0.0.1-10.0.0.2" "-i" "1.2.3.4" "-b")
;(-main "-c" "sleep 10000" "-r" "10.0.0.1-10.0.0.2" "-i" "1.2.3.4" "-b")
