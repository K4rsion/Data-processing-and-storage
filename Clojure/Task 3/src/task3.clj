(ns task3)

(defn parallel-filter
  ([pred coll]
   (let [n (. (Runtime/getRuntime) availableProcessors)
         chunk-size 200
         chunk (map doall (partition-all chunk-size coll))
         future-filter #(future (doall (filter pred %)))
         pool (map future-filter chunk)
         step (fn step [cur fut]
                (if-let [s (seq fut)]
                  (lazy-cat (deref (first cur)) (step (rest cur) (rest s)))
                  (mapcat deref cur)))]
     (step pool (drop n pool)))))