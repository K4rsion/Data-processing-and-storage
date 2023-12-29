(ns task3_test
  (:require [clojure.test :refer :all])
  (:use task3))

(deftest parallel-filter-test
  (is (= (filter odd? (range 0)) (parallel-filter odd? (range 0))))
  (is (= (filter even? (range 100)) (parallel-filter even? (range 100))))
  (is (= (filter even? (range 1000)) (parallel-filter even? (range 1000)))))

(deftest filter-time-test
  (time (doall (take 1000 (filter even? (iterate inc 0))))))

(deftest parallel-filter-time-test
  (time (doall (take 1000 (parallel-filter even? (iterate inc 0))))))