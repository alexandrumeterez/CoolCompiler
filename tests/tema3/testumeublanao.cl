-- Now class A inherits IO instead of Object!
class A inherits IO {
};

class B inherits A {
};

class C inherits A {
};

class D inherits B {};
class E inherits B {};

class F inherits C {};

class Main inherits E {
    x : SELF_TYPE;
    i(x : Object) : Object {
        case x of
            i : Int => 1231;
            s : String => 8989;
            a : A => 9898;
            m : Main => 9090;
        esac
    };

    main() : Object {
        {
            i(100);
        }
    };
};