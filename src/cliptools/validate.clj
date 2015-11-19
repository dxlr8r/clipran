(ns cliptools.validate
  (:require [cliptools.calculate :refer [split-ip split-range ip->int]]))

(def ^:private ip-pattern #"\d+\.\d+\.\d+\.\d+")

(defn ip? [ip]
  (if (re-matches ip-pattern ip)
    (= 4 (count (take-while #(> 256 %) (split-ip ip))))
    false))

(defn ip-range? "Is 'from' and 'to' a range?" [from to]
  (and
   (ip? from)
   (ip? to)
   (< (ip->int from) (ip->int to))))

(defn range-str? "Is string a range?" [rng-str]
  (let [range-pattern (re-pattern (str ip-pattern "-" ip-pattern))]
    (if (re-matches range-pattern rng-str) true false)))

(defn ip-range-str? "Is string a valid IP range?" [ip-rng-str]
  (if (range-str? ip-rng-str) (apply ip-range? (split-range ip-rng-str)) false))

(defn ip-address-or-range? "Is argument an IP address or an IP range?" [arg]
  (if (ip? arg)
    "address"
    (if (ip-range-str? arg)
      "range"
      false)))
