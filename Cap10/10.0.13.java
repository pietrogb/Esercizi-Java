/*
Stampa:
R[] v = new R[3];
v[0] = (R) Naming.lookup("pippo");
v[1] = v[0].f(); // Stampe: "f" su server,
v[2] = (v[0].g()).m(); // Stampe: "g", "m" sul server
v[0].stampa(); // Stampe: "pippo", "plutopluto", "topolino" su server
v[1].stampa(); // Stampe: "pippo", "plutopluto", "topolino" su server
v[2].stampa(); // Stampe: "pippo", "pluto", "topolino"
*/