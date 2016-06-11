(ns simulate-5-sided-die.core-test
  (:require [clojure.test :refer :all]
            [simulate-5-sided-die.core :refer :all]))

(def ^:private MIN 1)
(def ^:private MAX 5)
(def ^:private SAMPLE_SIZE 10000)

(def ^:private EXPECTED_FREQUENCY (* SAMPLE_SIZE (/ MIN MAX)))
(def ^:private LIMIT_FREQUENCY (* SAMPLE_SIZE (/ (inc MIN) MAX)))
(def ^:private MARGIN_OF_ERROR (/ (- LIMIT_FREQUENCY EXPECTED_FREQUENCY) 16))

(defn- rand5-seq [n]
  (take n (repeatedly rand5)))

(defn- within-margin-of-error? [freq]
  (and (>= freq (- EXPECTED_FREQUENCY MARGIN_OF_ERROR))
       (<= freq (+ EXPECTED_FREQUENCY MARGIN_OF_ERROR))))

(defn- within-range? [value]
  (and (>= value MIN) (<= value MAX)))

(deftest rand5-test
  (let [sample (rand5-seq SAMPLE_SIZE)]
    (testing "probablity distribution"
      (is (every? within-margin-of-error? (vals (frequencies sample)))))
    (testing "range check"
      (is (every? within-range? sample)))))
