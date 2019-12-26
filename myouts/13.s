    .data
    .align 2
    .globl class_nameTab
    .globl Int_protObj
    .globl String_protObj
    .globl bool_const0
    .globl bool_const1
    .globl Main_protObj
    .globl _int_tag
    .globl _string_tag
    .globl _bool_tag
    _int_tag:
        .word 9
    _string_tag:
        .word 10
    _bool_tag:
        .word 11
    str_const0:
        .word 10
        .word 5
        .word String_dispTab
        .word int_const0
        .asciiz ""
        .align 2
    str_const1:
        .word 10
        .word 6
        .word String_dispTab
        .word int_const1
        .asciiz "Object"
        .align 2
    str_const2:
        .word 10
        .word 5
        .word String_dispTab
        .word int_const2
        .asciiz "IO"
        .align 2
    str_const3:
        .word 10
        .word 5
        .word String_dispTab
        .word int_const3
        .asciiz "A"
        .align 2
    str_const4:
        .word 10
        .word 5
        .word String_dispTab
        .word int_const3
        .asciiz "C"
        .align 2
    str_const5:
        .word 10
        .word 5
        .word String_dispTab
        .word int_const3
        .asciiz "F"
        .align 2
    str_const6:
        .word 10
        .word 5
        .word String_dispTab
        .word int_const3
        .asciiz "B"
        .align 2
    str_const7:
        .word 10
        .word 5
        .word String_dispTab
        .word int_const3
        .asciiz "E"
        .align 2
    str_const8:
        .word 10
        .word 6
        .word String_dispTab
        .word int_const4
        .asciiz "Main"
        .align 2
    str_const9:
        .word 10
        .word 5
        .word String_dispTab
        .word int_const3
        .asciiz "D"
        .align 2
    str_const10:
        .word 10
        .word 5
        .word String_dispTab
        .word int_const5
        .asciiz "Int"
        .align 2
    str_const11:
        .word 10
        .word 6
        .word String_dispTab
        .word int_const1
        .asciiz "String"
        .align 2
    str_const12:
        .word 10
        .word 6
        .word String_dispTab
        .word int_const4
        .asciiz "Bool"
        .align 2
    str_const13:
        .word 10
        .word 11
        .word String_dispTab
        .word int_const6
        .asciiz "13-attr-init-ref-assign.cl"
        .align 2
    str_const14:
        .word 10
        .word 5
        .word String_dispTab
        .word int_const5
        .asciiz "abc"
        .align 2
    str_const15:
        .word 10
        .word 5
        .word String_dispTab
        .word int_const5
        .asciiz "def"
        .align 2
    int_const0:
        .word 9
        .word 4
        .word Int_dispTab
        .word 0
    int_const1:
        .word 9
        .word 4
        .word Int_dispTab
        .word 6
    int_const2:
        .word 9
        .word 4
        .word Int_dispTab
        .word 2
    int_const3:
        .word 9
        .word 4
        .word Int_dispTab
        .word 1
    int_const4:
        .word 9
        .word 4
        .word Int_dispTab
        .word 4
    int_const5:
        .word 9
        .word 4
        .word Int_dispTab
        .word 3
    int_const6:
        .word 9
        .word 4
        .word Int_dispTab
        .word 26
    int_const7:
        .word 9
        .word 4
        .word Int_dispTab
        .word 100
    int_const8:
        .word 9
        .word 4
        .word Int_dispTab
        .word 117
    bool_const0:
        .word 11
        .word 4
        .word Bool_dispTab
        .word 0
    bool_const1:
        .word 11
        .word 4
        .word Bool_dispTab
        .word 1
    class_nameTab:
        .word str_const1
        .word str_const2
        .word str_const3
        .word str_const4
        .word str_const5
        .word str_const6
        .word str_const7
        .word str_const8
        .word str_const9
        .word str_const10
        .word str_const11
        .word str_const12
        .word str_const13
    class_objTab:
        .word Object_protObj
        .word Object_init
        .word IO_protObj
        .word IO_init
        .word A_protObj
        .word A_init
        .word C_protObj
        .word C_init
        .word F_protObj
        .word F_init
        .word B_protObj
        .word B_init
        .word E_protObj
        .word E_init
        .word Main_protObj
        .word Main_init
        .word D_protObj
        .word D_init
        .word Int_protObj
        .word Int_init
        .word String_protObj
        .word String_init
        .word Bool_protObj
        .word Bool_init
    Object_protObj:
    	.word 0
    	.word 3
    	.word Object_dispTab

    IO_protObj:
    	.word 1
    	.word 3
    	.word IO_dispTab

    A_protObj:
    	.word 2
    	.word 4
    	.word A_dispTab
    	.word int_const0
    C_protObj:
    	.word 3
    	.word 5
    	.word C_dispTab
    	.word int_const0
    	.word bool_const0
    F_protObj:
    	.word 4
    	.word 5
    	.word F_dispTab
    	.word int_const0
    	.word bool_const0
    B_protObj:
    	.word 5
    	.word 5
    	.word B_dispTab
    	.word int_const0
    	.word str_const0
    E_protObj:
    	.word 6
    	.word 5
    	.word E_dispTab
    	.word int_const0
    	.word str_const0
    Main_protObj:
    	.word 7
    	.word 6
    	.word Main_dispTab
    	.word int_const0
    	.word str_const0
    	.word 0
    D_protObj:
    	.word 8
    	.word 5
    	.word D_dispTab
    	.word int_const0
    	.word str_const0
    Int_protObj:
    	.word 9
    	.word 4
    	.word Int_dispTab
    	.word 0
    String_protObj:
    	.word 10
    	.word 5
    	.word String_dispTab
    	.asciiz ""
    	.align 2
    Bool_protObj:
    	.word 11
    	.word 4
    	.word Bool_dispTab
    	.word 0
    Object_dispTab:
        .word Object.abort
        .word Object.type_name
        .word Object.copy
    IO_dispTab:
        .word Object.abort
        .word Object.type_name
        .word Object.copy
        .word IO.out_string
        .word IO.out_int
        .word IO.in_string
        .word IO.in_int
    A_dispTab:
        .word Object.abort
        .word Object.type_name
        .word Object.copy
        .word IO.out_string
        .word IO.out_int
        .word IO.in_string
        .word IO.in_int
        .word A.f
    C_dispTab:
        .word Object.abort
        .word Object.type_name
        .word Object.copy
        .word IO.out_string
        .word IO.out_int
        .word IO.in_string
        .word IO.in_int
        .word A.f
        .word C.f
        .word C.h
    F_dispTab:
        .word Object.abort
        .word Object.type_name
        .word Object.copy
        .word IO.out_string
        .word IO.out_int
        .word IO.in_string
        .word IO.in_int
        .word A.f
        .word C.f
        .word C.h
    B_dispTab:
        .word Object.abort
        .word Object.type_name
        .word Object.copy
        .word IO.out_string
        .word IO.out_int
        .word IO.in_string
        .word IO.in_int
        .word A.f
        .word B.g
    E_dispTab:
        .word Object.abort
        .word Object.type_name
        .word Object.copy
        .word IO.out_string
        .word IO.out_int
        .word IO.in_string
        .word IO.in_int
        .word A.f
        .word B.g
    Main_dispTab:
        .word Object.abort
        .word Object.type_name
        .word Object.copy
        .word IO.out_string
        .word IO.out_int
        .word IO.in_string
        .word IO.in_int
        .word A.f
        .word B.g
        .word Main.main
    D_dispTab:
        .word Object.abort
        .word Object.type_name
        .word Object.copy
        .word IO.out_string
        .word IO.out_int
        .word IO.in_string
        .word IO.in_int
        .word A.f
        .word B.g
    Int_dispTab:
        .word Object.abort
        .word Object.type_name
        .word Object.copy
    String_dispTab:
        .word Object.abort
        .word Object.type_name
        .word Object.copy
        .word String.length
        .word String.concat
        .word String.substr
    Bool_dispTab:
        .word Object.abort
        .word Object.type_name
        .word Object.copy



    .globl  heap_start
    heap_start:
        .word   0
        .text
        .globl  Int_init
        .globl  String_init
        .globl  Bool_init
        .globl  Main_init
        .globl  Main.main
    Object_init:
        addiu   $sp $sp -12
        sw      $fp 12($sp)
        sw      $s0 8($sp)
        sw      $ra 4($sp)
        addiu   $fp $sp 4
        move    $s0 $a0
        move    $a0 $s0
        lw      $fp 12($sp)
        lw      $s0 8($sp)
        lw      $ra 4($sp)
        addiu   $sp $sp 12
        jr      $ra
    IO_init:
        addiu   $sp $sp -12
        sw      $fp 12($sp)
        sw      $s0 8($sp)
        sw      $ra 4($sp)
        addiu   $fp $sp 4
        move    $s0 $a0
        jal     Object_init
        move    $a0 $s0
        lw      $fp 12($sp)
        lw      $s0 8($sp)
        lw      $ra 4($sp)
        addiu   $sp $sp 12
        jr      $ra
    Int_init:
        addiu   $sp $sp -12
        sw      $fp 12($sp)
        sw      $s0 8($sp)
        sw      $ra 4($sp)
        addiu   $fp $sp 4
        move    $s0 $a0
        jal     Object_init
        move    $a0 $s0
        lw      $fp 12($sp)
        lw      $s0 8($sp)
        lw      $ra 4($sp)
        addiu   $sp $sp 12
        jr      $ra
    String_init:
        addiu   $sp $sp -12
        sw      $fp 12($sp)
        sw      $s0 8($sp)
        sw      $ra 4($sp)
        addiu   $fp $sp 4
        move    $s0 $a0
        jal     Object_init
        move    $a0 $s0
        lw      $fp 12($sp)
        lw      $s0 8($sp)
        lw      $ra 4($sp)
        addiu   $sp $sp 12
        jr      $ra
    Bool_init:
        addiu   $sp $sp -12
        sw      $fp 12($sp)
        sw      $s0 8($sp)
        sw      $ra 4($sp)
        addiu   $fp $sp 4
        move    $s0 $a0
        jal     Object_init
        move    $a0 $s0
        lw      $fp 12($sp)
        lw      $s0 8($sp)
        lw      $ra 4($sp)
        addiu   $sp $sp 12
        jr      $ra
    A_init:
        addiu   $sp $sp -12
        sw      $fp 12($sp)
        sw      $s0 8($sp)
        sw      $ra 4($sp)
        addiu   $fp $sp 4
        move    $s0 $a0
        jal     IO_init
        la      $a0 int_const7
        sw     $a0 12($s0)
        move    $a0 $s0
        lw      $fp 12($sp)
        lw      $s0 8($sp)
        lw      $ra 4($sp)
        addiu   $sp $sp 12
        jr      $ra
    B_init:
        addiu   $sp $sp -12
        sw      $fp 12($sp)
        sw      $s0 8($sp)
        sw      $ra 4($sp)
        addiu   $fp $sp 4
        move    $s0 $a0
        jal     A_init
        la      $a0 str_const14
        sw     $a0 16($s0)
        move    $a0 $s0
        lw      $fp 12($sp)
        lw      $s0 8($sp)
        lw      $ra 4($sp)
        addiu   $sp $sp 12
        jr      $ra
    C_init:
        addiu   $sp $sp -12
        sw      $fp 12($sp)
        sw      $s0 8($sp)
        sw      $ra 4($sp)
        addiu   $fp $sp 4
        move    $s0 $a0
        jal     A_init
        la      $a0 bool_const1
        sw     $a0 16($s0)
        move    $a0 $s0
        lw      $fp 12($sp)
        lw      $s0 8($sp)
        lw      $ra 4($sp)
        addiu   $sp $sp 12
        jr      $ra
    D_init:
        addiu   $sp $sp -12
        sw      $fp 12($sp)
        sw      $s0 8($sp)
        sw      $ra 4($sp)
        addiu   $fp $sp 4
        move    $s0 $a0
        jal     B_init
        move    $a0 $s0
        lw      $fp 12($sp)
        lw      $s0 8($sp)
        lw      $ra 4($sp)
        addiu   $sp $sp 12
        jr      $ra
    E_init:
        addiu   $sp $sp -12
        sw      $fp 12($sp)
        sw      $s0 8($sp)
        sw      $ra 4($sp)
        addiu   $fp $sp 4
        move    $s0 $a0
        jal     B_init
        move    $a0 $s0
        lw      $fp 12($sp)
        lw      $s0 8($sp)
        lw      $ra 4($sp)
        addiu   $sp $sp 12
        jr      $ra
    F_init:
        addiu   $sp $sp -12
        sw      $fp 12($sp)
        sw      $s0 8($sp)
        sw      $ra 4($sp)
        addiu   $fp $sp 4
        move    $s0 $a0
        jal     C_init
        move    $a0 $s0
        lw      $fp 12($sp)
        lw      $s0 8($sp)
        lw      $ra 4($sp)
        addiu   $sp $sp 12
        jr      $ra
    Main_init:
        addiu   $sp $sp -12
        sw      $fp 12($sp)
        sw      $s0 8($sp)
        sw      $ra 4($sp)
        addiu   $fp $sp 4
        move    $s0 $a0
        jal     E_init
        move    $a0 $s0
        lw      $fp 12($sp)
        lw      $s0 8($sp)
        lw      $ra 4($sp)
        addiu   $sp $sp 12
        jr      $ra
    A.f:
        addiu   $sp $sp -12
        sw      $fp 12($sp)
        sw      $s0 8($sp)
        sw      $ra 4($sp)
        addiu   $fp $sp 4
        move    $s0 $a0
        la      $a0 int_const3
        lw      $fp 12($sp)
        lw      $s0 8($sp)
        lw      $ra 4($sp)
        addiu   $sp $sp 12
        jr      $ra
    B.g:
        addiu   $sp $sp -12
        sw      $fp 12($sp)
        sw      $s0 8($sp)
        sw      $ra 4($sp)
        addiu   $fp $sp 4
        move    $s0 $a0
        la      $a0 int_const2
        lw      $fp 12($sp)
        lw      $s0 8($sp)
        lw      $ra 4($sp)
        addiu   $sp $sp 12
        jr      $ra
    C.f:
        addiu   $sp $sp -12
        sw      $fp 12($sp)
        sw      $s0 8($sp)
        sw      $ra 4($sp)
        addiu   $fp $sp 4
        move    $s0 $a0
        la      $a0 int_const5
        lw      $fp 12($sp)
        lw      $s0 8($sp)
        lw      $ra 4($sp)
        addiu   $sp $sp 12
        jr      $ra
    C.h:
        addiu   $sp $sp -12
        sw      $fp 12($sp)
        sw      $s0 8($sp)
        sw      $ra 4($sp)
        addiu   $fp $sp 4
        move    $s0 $a0
        la      $a0 int_const4
        lw      $fp 12($sp)
        lw      $s0 8($sp)
        lw      $ra 4($sp)
        addiu   $sp $sp 12
        jr      $ra
    Main.main:
        addiu   $sp $sp -12
        sw      $fp 12($sp)
        sw      $s0 8($sp)
        sw      $ra 4($sp)
        addiu   $fp $sp 4
        move    $s0 $a0
        	lw  $a0 12($s0)
            sw      $a0 0($sp)
            addiu   $sp $sp -4
        move    $a0 $s0
        bnez    $a0 dispatch0
        la      $a0 str_const13
        li      $t1 31
        jal     _dispatch_abort
        dispatch0:
        lw      $t1 8($a0)          # dispatch table
        lw      $t1 16($t1) # method offset
        jalr    $t1
        la      $a0 int_const8
        sw      $a0 12($s0)
        	lw  $a0 12($s0)
            sw      $a0 0($sp)
            addiu   $sp $sp -4
        move    $a0 $s0
        bnez    $a0 dispatch2
        la      $a0 str_const13
        li      $t1 33
        jal     _dispatch_abort
        dispatch2:
        lw      $t1 8($a0)          # dispatch table
        lw      $t1 16($t1) # method offset
        jalr    $t1
        	lw  $a0 16($s0)
            sw      $a0 0($sp)
            addiu   $sp $sp -4
        move    $a0 $s0
        bnez    $a0 dispatch4
        la      $a0 str_const13
        li      $t1 35
        jal     _dispatch_abort
        dispatch4:
        lw      $t1 8($a0)          # dispatch table
        lw      $t1 12($t1) # method offset
        jalr    $t1
        la      $a0 str_const15
        sw      $a0 16($s0)
        	lw  $a0 16($s0)
            sw      $a0 0($sp)
            addiu   $sp $sp -4
        move    $a0 $s0
        bnez    $a0 dispatch6
        la      $a0 str_const13
        li      $t1 37
        jal     _dispatch_abort
        dispatch6:
        lw      $t1 8($a0)          # dispatch table
        lw      $t1 12($t1) # method offset
        jalr    $t1
        lw      $fp 12($sp)
        lw      $s0 8($sp)
        lw      $ra 4($sp)
        addiu   $sp $sp 12
        jr      $ra