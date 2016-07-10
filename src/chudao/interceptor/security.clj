(ns chudao.interceptor.security
  (:require [chudao.service.data :as data]))

(defn- public-path
  [uri]
  (.startsWith uri "/auth/"))

(defn- already-authenticated
  [ring-session]
  (data/user-already-authenticated? ring-session))

(defbefore check-auth-status
  [context]
  (if (or (public-path (get-in context [:request :uri]))
          (already-authenticated (get-in context [:request :headers :cookies "ring-session"])))
    context
    (-> context
        terminate
        (assoc-in [:response] {:status 404}))))
