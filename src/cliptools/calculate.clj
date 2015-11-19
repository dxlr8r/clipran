(ns cliptools.calculate
  (:require [clojure.string :refer [split replace] :rename {replace str-replace}])
  (:import [java.lang Math]))

(defn- floor [x] (bigint (Math/floor x)))
(defn- expt [decimal exp] (apply * (repeat exp decimal)))

(defn- iph "Convert decimal octet <-> integer helper"
  ([n]   (iph 1 n))
  ([x n] (* x (expt 256 n))))

(defn split-ip "Split IP string into a list" [ip-str]
  (map #(Integer/parseInt %) (split ip-str #"\.")))

(defn split-range "Split range string into a vector" [rng-str]
  (split rng-str #"-"))

(defn ip->int [ip]
  (let [[a b c d] (split-ip ip)]
    (+ (iph a 3) (iph b 2) (iph c 1) d)))

(defn int->ip [i]
  (let [a (floor (/ i ,  (iph 3)))
        b (floor (/ (- i (iph a 3)) , (iph 2)))
        c (floor (/ (- i (iph a 3)    (iph b 2)) , (iph 1)))
        d (          - i (iph a 3)  , (iph b 2)  , (iph c 1))]
    (str a \. b \. c \. d)))

(defn ip-math-operation "Does a math operation to an IP address" [operation ip operator]
  (int->ip (operation (ip->int ip) operator)))

(defn ip-range->ip-list "Generates a list of IP addresses" [from to]
  (let [from (ip->int from)
        to (ip->int to)]
    (for [counter (range (inc (- to from)))] (int->ip (+ from counter)))))
