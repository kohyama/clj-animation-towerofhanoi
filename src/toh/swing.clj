(ns toh.swing
    (:require [toh.core :refer (disk-states)]
              [toh.twod :refer (rectangles-fn)]))

(def fpg (atom nil))

(defn close! []
  (if-let [f (:frame @fpg)]
    (do (.setVisible f false)
        (.dispose f)
        (reset! fpg nil))))

(defn open! [title width height]
  (if @fpg (close!))
  (let [f (javax.swing.JFrame. title)
        _ (.setSize f width (+ 24 height))
        c (.getContentPane f)
        p (javax.swing.JPanel.)
        _ (.add c p)
        _ (.setVisible f true)
        g (.getGraphics p)
        i (.createImage p width height)
        h (.getGraphics i)]
    (reset! fpg {:frame f :panel p :graphics g
                 :oi i :og h})))

(defmacro g [& body]
  `(if-let [h# (:og @fpg)]
     (doto h# ~@body)))

(defmacro p [margin-x margin-y]
  `(when-let [{g# :graphics i# :oi p# :panel} @fpg]
     (.drawImage g# i# ~margin-x ~margin-y p#)))

(defn display [n w unit]
  (let [fw (* (+ 3 (* 6 (inc n))) unit)
        fh (* (+ 5 n) unit)
        rectangles (rectangles-fn n unit)]
    (open! (str "Tower of Hanoi: " n " disks") fw fh)
    (doseq [state (disk-states n)]
      (g (.clearRect 0 0 fw fh))
      (doseq [[x y w h [hu sa br]] (rectangles state)]
        (g (.setColor (java.awt.Color/getHSBColor hu sa br))
           (.fillRect x y w h)))
      (p unit unit)
      (Thread/sleep w))))
