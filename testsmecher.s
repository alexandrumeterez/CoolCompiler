-- Now class A inherits IO instead of Object!
class A inherits IO {
    a : Int <- 100;

    f() : Int { 1 };
};

class B inherits A {
    b : String <- "abc";

    g() : Int { 2 };
};

class C inherits A {
    c : Bool <- true;

    f() : Int { 3 };
    h() : Int { 4 };
};

class D inherits B {};
class E inherits B {};

class F inherits C {};

class Main inherits E {
    x : SELF_TYPE;

    fact_rec(n : Int) : Int {
        if n = 0 then 1 else n * fact_rec(n-1) fi
    };

    fib(n : Int) : Int {
        if n = 1 then 1 else
            if n = 2 then 1 else
                fib(n-1) + fib(n-2)
            fi
        fi
    };

    main() : Object {
        {
            out_string("abc");
            out_int(100);
            out_string("Rez este: ").out_int(fib(4)).out_string("\n");
        }
    };
};