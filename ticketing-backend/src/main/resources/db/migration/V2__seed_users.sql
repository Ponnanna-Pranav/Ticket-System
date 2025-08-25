-- password hashes will be set by application runner if absent,
-- here we create users and roles links, and the runner will update hashes.
insert into users(id,email,password_hash,name) values
  (gen_random_uuid(),'admin@demo.com','{bcrypt}$2a$10$2o5Vw8o8o8o8o8o8o8o8uuz1r8xg1Tzv2xVQ1','Admin'),
  (gen_random_uuid(),'agent@demo.com','{bcrypt}$2a$10$2o5Vw8o8o8o8o8o8o8o8uuz1r8xg1Tzv2xVQ1','Agent'),
  (gen_random_uuid(),'user@demo.com','{bcrypt}$2a$10$2o5Vw8o8o8o8o8o8o8o8uuz1r8xg1Tzv2xVQ1','User');
-- Assign roles by matching emails at startup runner
