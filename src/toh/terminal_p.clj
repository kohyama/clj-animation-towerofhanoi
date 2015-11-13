(ns toh.terminal-p
  (:require [clojure.test :refer (with-test are run-tests)]
            [toh.core :refer (peg-states)]))

(with-test
  (defn- represent-fn [n]
    (let [w (+ (* 6 (inc n)) 2)
          h (+ n 3)
          w-1 (dec w)
          h-1 (dec h)
          base-and-pegs (persistent!
                          (reduce 
                            (fn [a [x y c]] (assoc! a (+ (* y w) x) c))
                            (transient (vec (repeat (* h w) \space)))
                            (concat (for [y (range 0 h)]   [w-1 y \newline])
                                    (for [x (range 0 w-1)] [x h-1 \=])
                                    (for [pi (range 0 3) y (range 1 h-1)]
                                      [(* (inc (* 2 pi)) (inc n)) y \|]))))]
      (fn [state]
        (apply str
          (persistent!
            (reduce
              (fn [a [x y]] (assoc! a (+ (* y w) x) \-))
              (transient base-and-pegs)
              (for [[pi peg] (map-indexed vector state)
                    [i y] (map vector (reverse peg) (range (inc n) -2 -1))
                    x (concat
                        (take i (iterate inc (+ 1 (* 2 (inc n) pi) (- n i))))
                        (take i (iterate inc (+ 2 (* 2 (inc n) pi) n))))]
                [x y])))))))

  (are [n state _ r] (= ((represent-fn n) state) r)
    2 '[(1 2) (   ) (   )] -> (str "                   \n"
                                   "   |     |     |   \n"
                                   "  -|-    |     |   \n"
                                   " --|--   |     |   \n"
                                   "===================\n")
    2 '[(  2) (  1) (   )] -> (str "                   \n"
                                   "   |     |     |   \n"
                                   "   |     |     |   \n"
                                   " --|--  -|-    |   \n"
                                   "===================\n")
    2 '[(   ) (  1) (  2)] -> (str "                   \n"
                                   "   |     |     |   \n"
                                   "   |     |     |   \n"
                                   "   |    -|-  --|-- \n"
                                   "===================\n")
    2 '[(   ) (   ) (1 2)] -> (str "                   \n"
                                   "   |     |     |   \n"
                                   "   |     |    -|-  \n"
                                   "   |     |   --|-- \n"
                                   "===================\n")))

(defn display [n w]
  (print "\033[2J")
  (doseq [rep (map (represent-fn n) (peg-states n))]
    (print "\033[2;1H")
    (print rep)
    (flush)
    (Thread/sleep w)))
