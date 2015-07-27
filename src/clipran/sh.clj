(ns clipran.sh
  (:import [java.io BufferedReader InputStreamReader]))

(defn- cmd [p] (.. Runtime getRuntime (exec (str p))))

(defn- cmdout [o]
  (let [r (BufferedReader.
           (InputStreamReader.
            (.getInputStream o)))]
    (line-seq r)
    ))

(defn sh [arg]
  (cmdout (cmd arg)))
