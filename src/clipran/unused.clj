(ns clipran.unused
  (:import [java.net InetAddress]))


(map #(keyword %) (map #(str %) (range 4)))

(zipmap  '(:0 :1 :2 :3) '(a b c d))

(Integer/parseInt "11111111" 2)
(Integer/toString 255 2)

(.toString (InetAddress/getByAddress (.toByteArray (BigInteger/valueOf (long 1045026394)))))


(.. Runtime (getRuntime) (exec "ls"))
(.. (Runtime/getRuntime) (exec "ls"))
(.exec (Runtime/getRuntime) "ls")

(clojure.set/union (list 1 2) (list 3 4)) ; into
(reduce conj '(1,2,3) '(3,4,5,1))

(sh "sleep 10000 1>/dev/null 2>&1 & echo -n $!")
