(ns demo-linter-error.core
  [:import [org.apache.commons.io IOUtils]]
  (:gen-class))

(defn output [& args] nil)

(defn current-time []
  (double (/ (System/currentTimeMillis) 1000)))

(defn reconnecting-write
  [connect-fn prefix ^java.io.PrintWriter out rollup]
  (try
    (let [socket (if (and out (not (.checkError out)))
                   out
                   (do (IOUtils/closeQuietly out)
                       (connect-fn)))]
      (output prefix socket rollup (long (current-time))))
    (catch Exception e
      (IOUtils/closeQuietly out))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [pw (java.io.PrintWriter. System/out)]
    (reconnecting-write (fn [] (java.io.PrintWriter. System/out)) "foo" pw [:a :b :c])))
