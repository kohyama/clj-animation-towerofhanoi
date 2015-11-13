(ns toh.twod)

(defn rectangles-fn [n unit]
  (let [base [[0
               (* (+ 2 n) unit)
               (* (+ (* 6 n) 7) unit)
               unit
               [0.0 1.0 0.3]]]
        pegs (for [pi '(0 1 2)]
               [(* (inc (* 2 pi)) (inc n) unit)
                unit
                unit
                (* (inc n) unit)
                [0.0 1.0 0.3]])
        colors (mapv #(vector (float (/ % n)) 0.8 1.0) (range n))]
    (fn [state]
      (concat base pegs
        ; disks
        (for [[i [ux uy]] state]
          [(* (- ux i) unit)
           (* uy unit)
           (* (inc (* 2 i)) unit)
           unit
           (colors (dec i))])))))

