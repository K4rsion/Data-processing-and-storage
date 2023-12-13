(ns task1)
(use '[clojure.string :only [ends-with?]])

(defn generator [alphabet words]
  (reduce (fn [strings elem]
            (concat strings
                    (map #(str elem %)
                         (filter #(not (ends-with? elem %))
                                 alphabet))))
          ()
          words))

(defn generate-language [alphabet n]
  (reduce (fn [words _] (generator alphabet words))
          alphabet
          (range (- n 1))))

(println (generate-language ["a", "b", "c"]  3))
