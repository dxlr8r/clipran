(ns cliptools.validate
  (:require [cliptools.calculate :refer [split-ip split-range ip->int]]))

(def ^:private ip-pattern #"\d+\.\d+\.\d+\.\d+")

(defn ip? [arg]
  (if (re-matches ip-pattern arg)
    (= 4 (count (take-while #(> 256 %) (split-ip arg))))
    false))

(defn ip-range? [from to]
  (and
   (ip? from)
   (ip? to)
   (< (ip->int from) (ip->int to))))

(defn range-str? [arg]
  (let [range-pattern (re-pattern (str ip-pattern "-" ip-pattern))]
    (if (re-matches range-pattern arg) true false)))

(defn ip-range-str? [arg]
  (if (range-str? arg) (apply ip-range? (split-range arg)) false))

(defn ip-address-or-range? [arg]
  (if (ip? arg)
    "address"
    (if (ip-range? arg)
      "range"
      false)))
