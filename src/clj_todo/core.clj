(ns clj-todo.core
  (:use compojure.core [ring.adapter.jetty :only [run-jetty]] )
    (:require [compojure.route :as route]
              [monger core util]
              [compojure.handler :as handler]
              [clj-todo.todo :as todo ] ))



(defroutes main-routes
  ; what's going on
  
  (GET "/" [] (todo/index ) )
  (GET "/todos/" [] (todo/index ) )
  
  (PUT "/todos/" { params :params  body :body }  (todo/create params body) )
  ; resource actions 
  (POST "/todos/:id" { {id :id} :params  params :params  body :body }  (todo/lookup id params body) )
  (POST "/todos/:id/edit" { {id :id} :params  params :params  body :body }  (todo/edit id params body) )
  (DELETE "/todos/:id" { {id :id} :params  params :params  body :body }  (todo/delete id params body) )
  
  ; fall backs 
  (route/resources "/")
  (route/not-found "Page not found")   )



(prn (monger.core/connect! { :host (get (System/getenv) "database_host" "unknown") :port 33067 } ) )

(prn (monger.core/authenticate "todos" (get (System/getenv) "database_username" "") (.toCharArray (get (System/getenv) "database_password" ""))))

(prn (monger.core/set-db! (monger.core/get-db "todos")))


(def app
    (handler/api main-routes))


(defn -main [port]
    (run-jetty app {:port (Integer. port)}))
