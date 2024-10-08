(ns task5)

(def philosophers-number 5)
(def thinking-time 1000)
(def dining-time 1000)
(def iterations 1)

(def forks (take philosophers-number (repeatedly #(ref 0))))
(def transaction-restarts (atom 0 :validator #(>= % 0)))

(defn philosopher [number left-fork right-fork]
  (dotimes [_ iterations]
    (do
      (println (str "The philosopher " number " is thinking"))
      (Thread/sleep thinking-time)
      (dosync
        (swap! transaction-restarts inc)
        (alter left-fork inc)
        (println (str "The philosopher " number " picked up the left fork"))
        (alter right-fork inc)
        (println (str "The philosopher " number " picked up the right fork"))
        (Thread/sleep dining-time)
        (println (str "The philosopher " number " finished the meal"))
        (swap! transaction-restarts dec)))))

(def philosophers (map #(new Thread (fn [] (philosopher % (nth forks %) (nth forks (mod (inc %) philosophers-number))))) (range philosophers-number)))

(defn dining-philosophers []
  (do
    (doall (map #(.start %) philosophers))
    (doall (map #(.join %) philosophers))))

(time (dining-philosophers))
(println "Transaction restarts: " @transaction-restarts)