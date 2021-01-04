########################################################
# Setup binding to a database.
########################################################
D4M.dbinit()
@isdefined(namesp) || (namesp="")      # SET LOCAL LABEL TO AVOID COLLISIONS.

DB = D4M.dbsetup("uno","db.conf")    # Create binding to database.  Shorthand for:
stem = "flat"
Tccd = D4M.getindex(DB, namesp*stem, namesp*stem*"T")    # Create database table pair for holding adjacency matrix.
TccdDeg = D4M.getindex(DB, namesp*stem*"Deg")
D4M.addColCombiner(TccdDeg,"Degree,","sum");
