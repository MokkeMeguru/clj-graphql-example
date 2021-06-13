(ns server-utils.infrastructure.router.util)

(defn form-wrap-cors
  "Wrap the server response in a Control-Allow-Origin Header to
  allow connections from the web app."
  [opts]
  (fn [handler]
    (fn [request]
      (let [response (handler request)]
        (-> response
            (assoc-in [:headers "Access-Control-Allow-Credentials"] (or (:allow-credential opts) "true"))
            (assoc-in [:headers "Access-Control-Allow-Origin"] (or (:allow-origin opts) "*"))
            (assoc-in [:headers "Access-Control-Allow-Headers"] (or (:allow-headers opts) "authorization,content-type"))
            (assoc-in [:headers "Access-Control-Allow-Methods"] (or (:allow-methods opts) "POST,GET,OPTIONS,DELETE,PUT,UPDATE,PATCH")))))))
