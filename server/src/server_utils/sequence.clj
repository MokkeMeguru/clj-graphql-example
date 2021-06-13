(ns server-utils.sequence)

(defn find-first [expr sequence]
  (some (fn [element] (when (expr element) element)) sequence))
