(ns clipran.tools)

(defn cprintln [arg]
 (println
  (clojure.string/join
   "\n"
   (for [string arg] string))))
