(defproject clipran "1.0.0"
  :description "Generate commands from a range of IP's and map them to a single IP"
  :url "https://github.com/dxlr8r"
  :license {:name "Modified BSD license"
            :url "https://github.com/dxlr8r/clipran/blob/master/LICENSE"}
  :main clipran.core
  :aot [clipran.core]
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-pid "0.1.2"]
                 [org.clojure/tools.cli "0.3.1"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 [lein-light-nrepl "0.1.0"]]
  :repl-options {:nrepl-middleware [lighttable.nrepl.handler/lighttable-ops]}
  :plugins [[lein-gen "0.2.1"]
            [lein-expectations "0.0.7"]]
  :generators [[lein-gen/generators "0.2.1"]] ;; lein generate namespace bar.core => {src,test}/bar/core.clj


  :profiles {
             :dev {:dependencies [[expectations "2.1.0"]]}
             :user {:plugins [[lein-ancient "0.6.5"]]}}

  ;:jvm-opts ["-XX:-AllowUserSignalHandlers"]
  ;:javac-options ["-XX:+UseAltSigs"]
  )
