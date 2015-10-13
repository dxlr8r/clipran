(ns cliptools.generate
  (:require
   [cliptools.calculate :refer [split-range ip-range->ip-list]]
   [cliptools.validate :refer [range-str? ip-range?]]))


(defn ip-list "Generates a list of IP addresses, with validation and rng-str"
  ([rng-str] (if (range-str? rng-str) (apply ip-list (split-range rng-str)) nil))
  ([from to] (if (ip-range? from to) (ip-range->ip-list from to) nil)))
