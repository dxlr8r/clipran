(ns clipran.core-test
  (:require [expectations :refer :all]))

;;(deftest a-test
;;  (testing "FIXME, I fail."
;;    (is (= 0 1))))

(expect 0 1)

(expect 2 (+ 1 1))
