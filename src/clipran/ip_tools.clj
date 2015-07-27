(ns clipran.ip-tools
  (:require [clojure.string :refer [split replace] :rename {replace str-replace}]
            [clojure.math.numeric-tower :refer [abs floor expt ]])
  (:import [java.net InetAddress]))

(def ^:private ip-pattern #"\d+\.\d+\.\d+\.\d+")

(defn- third [coll]
  (second (next coll)))

(defn- split-ip [ip]
  (map #(Integer/parseInt %) (split ip #"\.")))

(defn- split-range [arg]
  (let [part (split arg #"-")]
    [(first part) (last part)]))

(defn ip->int [ip]
  (let [part (split-ip ip)
        a (first part)
        b (second part)
        c (third part)
        d (last part)]
    (+ (* a (expt 256 3) ) (* b (expt 256 2)) (* c 256) d)))

(defn int->ip [ip]
  (let [p3 (expt 256 3)
        p2 (expt 256 2)
        a (floor (/ ip p3))
        b (floor (/ (- ip (* a p3)) p2))
        c (floor (/ (- ip (* a p3) (* b p2)) 256))
        d (          - ip (* a p3) (* b p2) (* c 256))]
    (str a \. b \. c \. d)))

(defn- generate-ips-from-range [from to]
  (let [from (ip->int from)
        to (ip->int to)]
    (for [counter (range (inc (- to from)))] (int->ip (+ from counter)))
    ))

(defn ip? [arg]
  (if (re-matches ip-pattern arg)
    (= 4 (count (take-while #(> 256 %) (split-ip arg))))
    false))

(defn range? [arg]
  (let [range-pattern (re-pattern (str ip-pattern "-" ip-pattern))]
    (if (re-matches range-pattern arg)
      (let [part (split-range arg)
            from (first part)
            to (last part)]
        (and
         (ip? from)
         (ip? to)
         (< (ip->int from) (ip->int to))))
      false)))

(defn address-or-range? [arg]
  (if (ip? arg)
    "address"
    (if (range? arg)
      "range"
      false)))

(defn range->ips [arg]
  (cond
   (= "address" (address-or-range? arg)) (list arg),
   (= "range" (address-or-range? arg))
   (let [part (split-range arg)]
     (generate-ips-from-range (first part) (last part))),
   :else false
   ))

(range->ips "62.73.214.90")
