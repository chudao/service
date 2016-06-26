(ns chudao.service.data)

(defn login-success
  [result]
  {:response-code "000"
   :response-message "login success"
   :user-id (:UserId result)
   :user-name (:UserName result)
   :auth-token "womenchudaola"})

(def login-failure
  {:response-code "001"
   :response-message "login failure: user not exists or password incorrect"})

(def register-failure-duplicate
  {:response-code "011"
   :response-message "registration failure: user already exists"})

(defn register-success
  [result]
  {:response-code "010"
   :response-message "registration success"
   :user-id (:generated_key result)
   :auth-token "womenchudaola"})

(def upload-success
  {:response-code "020"
   :response-message "upload success"})
