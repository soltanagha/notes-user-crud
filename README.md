# Notes service

  * User CRUD operation with basic Auth.
  * User has to has role (User roles are String for example 'ROLE_USER')
  * Note entity can be liked and unliked only by authorized users.
  * Each user one time can like Note.
  
## Technology stack:
  * Database emmbeded MongoDB with 'flapdoodle' dependency
  * Java Spring boot 3+ application
  * Java Spring security 5.4+
