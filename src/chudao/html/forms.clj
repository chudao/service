(ns chudao.html.forms
  (:require [net.cgrand.enlive-html :as html]
            [ring.util.response :as ring-resp]
            [io.pedestal.http :as bootstrap]))

(html/deftemplate form "templates/form.html" [])

(defn upload-photo
  [request]
  (ring-resp/response (apply str (form))))

