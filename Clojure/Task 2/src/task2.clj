(ns task2)
(use 'clojure.test)


(defn primes-gen
  [x table]
  (if (table x)
    (primes-gen (inc x)
                (reduce #(update %1 (+ x %2) conj %2)
                         (dissoc table x)
                         (table x)))
    (lazy-seq (cons x (primes-gen (inc x) (assoc table (* x x) [x]))))))


(def primes
  (lazy-seq (primes-gen 2 {})))


(deftest tests
  (is (= '(2 3 5 7 11 13 17 19 23 29) (take 10 primes))) 
  (is (= '(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541) (take 100 primes))))


(run-tests 'task2)