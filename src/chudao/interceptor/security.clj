(ns chudao.interceptor.security
  (:require [chudao.service.data :as data]
            [io.pedestal.interceptor.helpers :as helpers]
            [io.pedestal.interceptor.chain :as chain]
            ))

(defn- public-path
  [uri]
  (.startsWith uri "/auth/"))

(defn- already-authenticated
  [ring-session]
  (data/user-already-authenticated? ring-session))

(def check-auth-status
  (helpers/before
    (fn [context]
      (if (or (public-path (get-in context [:request :uri]))
              (already-authenticated (get-in context [:request :headers "x-auth-token"])))
        context
        (-> context
            chain/terminate
            (assoc-in [:response] {:status 401}))))))
