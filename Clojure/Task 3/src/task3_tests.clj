(ns task3_tests
  (:require [clojure.test :refer :all])
  (:use task3))

(deftest parallel-filter-test
  (is (= (filter odd? (range 0)) (parallel-filter odd? (range 0))))
  (is (= (filter even? (range 100)) (parallel-filter even? (range 100))))
  (is (= (filter even? (range 1000)) (parallel-filter even? (range 1000)))))

(deftest parallel-filter-time-test
  (time (doall (take 100 (filter even? (iterate inc 0)))))
  (time (doall (take 100 (parallel-filter even? (iterate inc 0))))))