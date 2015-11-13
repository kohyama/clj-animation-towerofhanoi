(ns toh.canvas
  (:require [clojure.browser.repl :as repl]
            [clojure.browser.dom :as dom]
            [cljs.core.async :refer (<! timeout)]
            [toh.core :refer (disk-states)]
            [toh.twod :refer (rectangles-fn)])
  (:require-macros
            [cljs.core.async.macros :refer (go)]))

(repl/connect "http://localhost:9000/repl")

(def cc (atom nil))

(defn open! [title width height]
  (if-let [cvs (:canvas @cc)]
    (do (set! (.-width cvs) width)
        (set! (.-height cvs) height))
    (let [;p (dom/element :div) something going wrong
          p (goog.dom/createElement "div")
          _ (dom/append (.-body js/document) p)
          ;cvs (dom/element :canvas {:width width :height height})
          cvs (goog.dom/createElement "canvas")
          _ (set! (.-width cvs) width)
          _ (set! (.-height cvs) height)
          _ (dom/append p cvs)
          ctx (.getContext cvs "2d")]
      (reset! cc {:canvas cvs :context ctx}))))

(defn hsb2rgb [[hu sa br]]
  (let [hu6 (* 6 hu)
        hi (int hu6)
        f (- hu6 hi)
        p (* br (- 1 sa))
        q (* br (- 1 (* f sa)))
        t (* br (- 1 (* (- 1 f) sa)))]
    (mapv #(int (* 255 %))
      ({0 [br t p] 1 [q br p] 2 [p br t]
        3 [p q br] 4 [t p br] 5 [br p q]} hi))))

(defn rgb-str [rgb]
  (apply str
    (concat ["rgb("] (interpose "," (map str rgb)) [")"])))

(defn display [n w unit]
  (let [fw (* (+ 3 (* 6 (inc n))) unit)
        fh (* (+ 5 n) unit)
        rectangles (rectangles-fn n unit)]
    (open! (str "Tower of Hanoi: " n " disks") fw fh)
    (go
      (loop [[state & r-states] (disk-states n)]
        (when state
          (when-let [ctx (:context @cc)]
            (.clearRect ctx 0 0 fw fh)
            (doseq [[x y w h hsb] (rectangles state)]
              (set! (.-fillStyle ctx) (rgb-str (hsb2rgb hsb)))
              (.fillRect ctx (+ x unit) (+ y unit) w h)))
          (<! (timeout w))
          (recur r-states))))))

(defn ^:export init []
 ;(display 3 10 10)
  )
