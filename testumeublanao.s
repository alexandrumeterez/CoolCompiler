    .data
    .align  2
    .globl  class_nameTab
    .globl  Int_protObj
    .globl  String_protObj
    .globl  bool_const0
    .globl  bool_const1
    .globl  Main_protObj
    .globl  _int_tag
    .globl  _string_tag
    .globl  _bool_tag
_int_tag:
    .word   4
_string_tag:
    .word   5
_bool_tag:
    .word   6
str_const0:
    .word   5
    .word   5
    .word   String_dispTab
    .word   int_const0
    .asciiz ""
    .align  2
str_const1:
    .word   5
    .word   6
    .word   String_dispTab
    .word   int_const6
    .asciiz "Object"
    .align  2
str_const2:
    .word   5
    .word   5
    .word   String_dispTab
    .word   int_const2
    .asciiz "IO"
    .align  2
str_const3:
    .word   5
    .word   5
    .word   String_dispTab
    .word   int_const3
    .asciiz "Int"
    .align  2
str_const4:
    .word   5
    .word   6
    .word   String_dispTab
    .word   int_const6
    .asciiz "String"
    .align  2
str_const5:
    .word   5
    .word   6
    .word   String_dispTab
    .word   int_const4
    .asciiz "Bool"
    .align  2
str_const6:
    .word   5
    .word   7
    .word   String_dispTab
    .word   int_const9
    .asciiz "32-big.cl"
    .align  2
str_const7:
    .word   5
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz " "
    .align  2
str_const8:
    .word   5
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz "\n"
    .align  2
str_const9:
    .word   5
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz "0"
    .align  2
str_const10:
    .word   5
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz "1"
    .align  2
str_const11:
    .word   5
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz "2"
    .align  2
str_const12:
    .word   5
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz "3"
    .align  2
str_const13:
    .word   5
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz "4"
    .align  2
str_const14:
    .word   5
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz "5"
    .align  2
str_const15:
    .word   5
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz "6"
    .align  2
str_const16:
    .word   5
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz "7"
    .align  2
str_const17:
    .word   5
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz "8"
    .align  2
str_const18:
    .word   5
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz "9"
    .align  2
str_const19:
    .word   5
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz "-"
    .align  2
str_const20:
    .word   5
    .word   5
    .word   String_dispTab
    .word   int_const1
    .asciiz "+"
    .align  2
str_const21:
    .word   5
    .word   6
    .word   String_dispTab
    .word   int_const4
    .asciiz "List"
    .align  2
str_const22:
    .word   5
    .word   6
    .word   String_dispTab
    .word   int_const4
    .asciiz "Main"
    .align  2
str_const23:
    .word   5
    .word   5
    .word   String_dispTab
    .word   int_const3
    .asciiz "A2I"
    .align  2
int_const0:
    .word   4
    .word   4
    .word   Int_dispTab
    .word   0
int_const1:
    .word   4
    .word   4
    .word   Int_dispTab
    .word   1
int_const2:
    .word   4
    .word   4
    .word   Int_dispTab
    .word   2
int_const3:
    .word   4
    .word   4
    .word   Int_dispTab
    .word   3
int_const4:
    .word   4
    .word   4
    .word   Int_dispTab
    .word   4
int_const5:
    .word   4
    .word   4
    .word   Int_dispTab
    .word   5
int_const6:
    .word   4
    .word   4
    .word   Int_dispTab
    .word   6
int_const7:
    .word   4
    .word   4
    .word   Int_dispTab
    .word   7
int_const8:
    .word   4
    .word   4
    .word   Int_dispTab
    .word   8
int_const9:
    .word   4
    .word   4
    .word   Int_dispTab
    .word   9
int_const10:
    .word   4
    .word   4
    .word   Int_dispTab
    .word   10
bool_const0:
    .word   6
    .word   4
    .word   Bool_dispTab
    .word   0
bool_const1:
    .word   6
    .word   4
    .word   Bool_dispTab
    .word   1
class_nameTab:
    .word   str_const1
    .word   str_const2
    .word   str_const21
    .word   str_const22
    .word   str_const3
    .word   str_const4
    .word   str_const5
    .word   str_const23
class_objTab:
    .word   Object_protObj
    .word   Object_init
    .word   IO_protObj
    .word   IO_init
    .word   List_protObj
    .word   List_init
    .word   Main_protObj
    .word   Main_init
    .word   Int_protObj
    .word   Int_init
    .word   String_protObj
    .word   String_init
    .word   Bool_protObj
    .word   Bool_init
    .word   A2I_protObj
    .word   A2I_init
Object_protObj:
    .word   0
    .word   3
    .word   Object_dispTab

IO_protObj:
    .word   1
    .word   3
    .word   IO_dispTab

List_protObj:
    .word   2
    .word   5
    .word   List_dispTab
    .word   0
    .word   0
Main_protObj:
    .word   3
    .word   3
    .word   Main_dispTab

Int_protObj:
    .word   4
    .word   4
    .word   Int_dispTab
    .word   0
String_protObj:
    .word   5
    .word   5
    .word   String_dispTab
    .word   int_const0
    .asciiz ""
    .align  2
Bool_protObj:
    .word   6
    .word   4
    .word   Bool_dispTab
    .word   0
A2I_protObj:
    .word   7
    .word   3
    .word   A2I_dispTab

Object_dispTab:
    .word   Object.abort
    .word   Object.type_name
    .word   Object.copy
IO_dispTab:
    .word   Object.abort
    .word   Object.type_name
    .word   Object.copy
    .word   IO.out_string
    .word   IO.out_int
    .word   IO.in_string
    .word   IO.in_int
List_dispTab:
    .word   Object.abort
    .word   Object.type_name
    .word   Object.copy
    .word   IO.out_string
    .word   IO.out_int
    .word   IO.in_string
    .word   IO.in_int
    .word   List.init
    .word   List.print
Main_dispTab:
    .word   Object.abort
    .word   Object.type_name
    .word   Object.copy
    .word   IO.out_string
    .word   IO.out_int
    .word   IO.in_string
    .word   IO.in_int
    .word   Main.main
Int_dispTab:
    .word   Object.abort
    .word   Object.type_name
    .word   Object.copy
String_dispTab:
    .word   Object.abort
    .word   Object.type_name
    .word   Object.copy
    .word   String.length
    .word   String.concat
    .word   String.substr
Bool_dispTab:
    .word   Object.abort
    .word   Object.type_name
    .word   Object.copy
A2I_dispTab:
    .word   Object.abort
    .word   Object.type_name
    .word   Object.copy
    .word   A2I.c2i
    .word   A2I.i2c
    .word   A2I.a2i
    .word   A2I.a2i_aux
    .word   A2I.i2a
    .word   A2I.i2a_aux
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
List_init:
    addiu   $sp $sp -12
    sw      $fp 12($sp)
    sw      $s0 8($sp)
    sw      $ra 4($sp)
    addiu   $fp $sp 4
    move    $s0 $a0
    jal     IO_init
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
    jal     IO_init
    move    $a0 $s0
    lw      $fp 12($sp)
    lw      $s0 8($sp)
    lw      $ra 4($sp)
    addiu   $sp $sp 12
    jr      $ra
A2I_init:
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
List.init:
    addiu   $sp $sp -12
    sw      $fp 12($sp)
    sw      $s0 8($sp)
    sw      $ra 4($sp)
    addiu   $fp $sp 4
    move    $s0 $a0
    lw      $a0 12($fp)
    sw      $a0 12($s0)
    lw      $a0 16($fp)
    sw      $a0 16($s0)
    move    $a0 $s0
    lw      $fp 12($sp)
    lw      $s0 8($sp)
    lw      $ra 4($sp)
    addiu   $sp $sp 12
    addiu   $sp $sp 8    # params free
    jr      $ra
List.print:
    addiu   $sp $sp -12
    sw      $fp 12($sp)
    sw      $s0 8($sp)
    sw      $ra 4($sp)
    addiu   $fp $sp 4
addiu   $sp $sp -4   # locals alloc
    move    $s0 $a0
lw      $a0 12($s0)
    bnez    $a0 case0
    la      $a0 str_const6
    li      $t1 24
    jal     _case_abort2
case0:
    sw      $a0 -4($fp)
    lw      $t1 0($a0)      # class tag
    blt     $t1 5 casebranch2
    bgt     $t1 5 casebranch2
    lw      $a0 -4($fp)
    b       endcase1
casebranch2:
    blt     $t1 4 casebranch4
    bgt     $t1 4 casebranch4
    lw      $a0 -4($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 A2I_protObj
    jal     Object.copy
    jal     A2I_init
    bnez    $a0 dispatch3
    la      $a0 str_const6
    li      $t1 26
    jal     _dispatch_abort
dispatch3:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 28($t1)   # method offset
    jalr    $t1
    b       endcase1
casebranch4:
    blt     $t1 0 casebranch6
    bgt     $t1 7 casebranch6
    move    $a0 $s0
    bnez    $a0 dispatch5
    la      $a0 str_const6
    li      $t1 27
    jal     _dispatch_abort
dispatch5:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 0($t1)   # method offset
    jalr    $t1
    la      $a0 str_const0
    b       endcase1
casebranch6:
    lw      $a0 -4($fp)
    jal     _case_abort
endcase1:
    sw      $a0 -4($fp)
    la      $a0 str_const7
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    lw      $a0 -4($fp)
    bnez    $a0 dispatch7
    la      $a0 str_const6
    li      $t1 31
    jal     _dispatch_abort
dispatch7:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 16($t1)   # method offset
    jalr    $t1
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    move    $a0 $s0
    bnez    $a0 dispatch8
    la      $a0 str_const6
    li      $t1 31
    jal     _dispatch_abort
dispatch8:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 12($t1)   # method offset
    jalr    $t1
    lw      $a0 16($s0)
    move    $t1 $a0
    la      $a0 bool_const1
    beqz    $t1 isvoid9
    la      $a0 bool_const0
isvoid9:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 else12
    la      $a0 str_const8
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    move    $a0 $s0
    bnez    $a0 dispatch10
    la      $a0 str_const6
    li      $t1 32
    jal     _dispatch_abort
dispatch10:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 12($t1)   # method offset
    jalr    $t1
    b       endif13
else12:
    lw      $a0 16($s0)
    bnez    $a0 dispatch11
    la      $a0 str_const6
    li      $t1 32
    jal     _dispatch_abort
dispatch11:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 32($t1)   # method offset
    jalr    $t1
endif13:
    addiu   $sp $sp 4    # locals free
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
    addiu   $sp $sp -8   # locals alloc
    move    $s0 $a0
    move    $a0 $zero
    sw      $a0 -4($fp)
    lw      $a0 -4($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 int_const0
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 List_protObj
    jal     Object.copy
    jal     List_init
    bnez    $a0 dispatch14
    la      $a0 str_const6
    li      $t1 43
    jal     _dispatch_abort
dispatch14:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 28($t1)   # method offset
    jalr    $t1
    sw      $a0 -8($fp)
    lw      $a0 -8($fp)
    bnez    $a0 dispatch15
    la      $a0 str_const6
    li      $t1 45
    jal     _dispatch_abort
dispatch15:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 32($t1)   # method offset
    jalr    $t1
    addiu   $sp $sp 8    # locals free
    lw      $fp 12($sp)
    lw      $s0 8($sp)
    lw      $ra 4($sp)
    addiu   $sp $sp 12
    jr      $ra
A2I.c2i:
    addiu   $sp $sp -12
    sw      $fp 12($sp)
    sw      $s0 8($sp)
    sw      $ra 4($sp)
    addiu   $fp $sp 4
    move    $s0 $a0
    lw      $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 str_const9
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq16
    la      $a1 bool_const0
    jal     equality_test
eq16:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 else45
    la      $a0 int_const0
    b       endif46
else45:
    lw      $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 str_const10
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq17
    la      $a1 bool_const0
    jal     equality_test
eq17:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 else43
    la      $a0 int_const1
    b       endif44
else43:
    lw      $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 str_const11
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq18
    la      $a1 bool_const0
    jal     equality_test
eq18:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 else41
    la      $a0 int_const2
    b       endif42
else41:
    lw      $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 str_const12
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq19
    la      $a1 bool_const0
    jal     equality_test
eq19:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 else39
    la      $a0 int_const3
    b       endif40
else39:
    lw      $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 str_const13
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq20
    la      $a1 bool_const0
    jal     equality_test
eq20:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 else37
    la      $a0 int_const4
    b       endif38
else37:
    lw      $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 str_const14
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq21
    la      $a1 bool_const0
    jal     equality_test
eq21:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 else35
    la      $a0 int_const5
    b       endif36
else35:
    lw      $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 str_const15
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq22
    la      $a1 bool_const0
    jal     equality_test
eq22:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 else33
    la      $a0 int_const6
    b       endif34
else33:
    lw      $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 str_const16
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq23
    la      $a1 bool_const0
    jal     equality_test
eq23:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 else31
    la      $a0 int_const7
    b       endif32
else31:
    lw      $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 str_const17
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq24
    la      $a1 bool_const0
    jal     equality_test
eq24:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 else29
    la      $a0 int_const8
    b       endif30
else29:
    lw      $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 str_const18
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq25
    la      $a1 bool_const0
    jal     equality_test
eq25:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 else27
    la      $a0 int_const9
    b       endif28
else27:
    move    $a0 $s0
    bnez    $a0 dispatch26
    la      $a0 str_const6
    li      $t1 79
    jal     _dispatch_abort
dispatch26:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 0($t1)   # method offset
    jalr    $t1
    la      $a0 int_const0
endif28:
endif30:
endif32:
endif34:
endif36:
endif38:
endif40:
endif42:
endif44:
endif46:
    lw      $fp 12($sp)
    lw      $s0 8($sp)
    lw      $ra 4($sp)
    addiu   $sp $sp 12
    addiu   $sp $sp 4    # params free
    jr      $ra
A2I.i2c:
    addiu   $sp $sp -12
    sw      $fp 12($sp)
    sw      $s0 8($sp)
    sw      $ra 4($sp)
    addiu   $fp $sp 4
    move    $s0 $a0
    lw      $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 int_const0
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq47
    la      $a1 bool_const0
    jal     equality_test
eq47:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 else76
    la      $a0 str_const9
    b       endif77
else76:
    lw      $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 int_const1
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq48
    la      $a1 bool_const0
    jal     equality_test
eq48:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 else74
    la      $a0 str_const10
    b       endif75
else74:
    lw      $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 int_const2
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq49
    la      $a1 bool_const0
    jal     equality_test
eq49:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 else72
    la      $a0 str_const11
    b       endif73
else72:
    lw      $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 int_const3
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq50
    la      $a1 bool_const0
    jal     equality_test
eq50:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 else70
    la      $a0 str_const12
    b       endif71
else70:
    lw      $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 int_const4
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq51
    la      $a1 bool_const0
    jal     equality_test
eq51:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 else68
    la      $a0 str_const13
    b       endif69
else68:
    lw      $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 int_const5
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq52
    la      $a1 bool_const0
    jal     equality_test
eq52:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 else66
    la      $a0 str_const14
    b       endif67
else66:
    lw      $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 int_const6
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq53
    la      $a1 bool_const0
    jal     equality_test
eq53:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 else64
    la      $a0 str_const15
    b       endif65
else64:
    lw      $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 int_const7
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq54
    la      $a1 bool_const0
    jal     equality_test
eq54:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 else62
    la      $a0 str_const16
    b       endif63
else62:
    lw      $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 int_const8
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq55
    la      $a1 bool_const0
    jal     equality_test
eq55:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 else60
    la      $a0 str_const17
    b       endif61
else60:
    lw      $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 int_const9
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq56
    la      $a1 bool_const0
    jal     equality_test
eq56:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 else58
    la      $a0 str_const18
    b       endif59
else58:
    move    $a0 $s0
    bnez    $a0 dispatch57
    la      $a0 str_const6
    li      $t1 97
    jal     _dispatch_abort
dispatch57:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 0($t1)   # method offset
    jalr    $t1
    la      $a0 str_const0
endif59:
endif61:
endif63:
endif65:
endif67:
endif69:
endif71:
endif73:
endif75:
endif77:
    lw      $fp 12($sp)
    lw      $s0 8($sp)
    lw      $ra 4($sp)
    addiu   $sp $sp 12
    addiu   $sp $sp 4    # params free
    jr      $ra
A2I.a2i:
    addiu   $sp $sp -12
    sw      $fp 12($sp)
    sw      $s0 8($sp)
    sw      $ra 4($sp)
    addiu   $fp $sp 4
    move    $s0 $a0
    lw      $a0 12($fp)
    bnez    $a0 dispatch78
    la      $a0 str_const6
    li      $t1 110
    jal     _dispatch_abort
dispatch78:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 12($t1)   # method offset
    jalr    $t1
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 int_const0
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq79
    la      $a1 bool_const0
    jal     equality_test
eq79:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 else95
    la      $a0 int_const0
    b       endif96
else95:
    la      $a0 int_const1
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 int_const0
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    lw      $a0 12($fp)
    bnez    $a0 dispatch80
    la      $a0 str_const6
    li      $t1 111
    jal     _dispatch_abort
dispatch80:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 20($t1)   # method offset
    jalr    $t1
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 str_const19
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq81
    la      $a1 bool_const0
    jal     equality_test
eq81:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 else93
    lw      $a0 12($fp)
    bnez    $a0 dispatch82
    la      $a0 str_const6
    li      $t1 111
    jal     _dispatch_abort
dispatch82:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 12($t1)   # method offset
    jalr    $t1
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 int_const1
    jal     Object.copy
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    lw      $t1 12($t1)     # int slot
    lw      $t2 12($a0)     # int slot
    sub     $t1 $t1 $t2
    sw      $t1 12($a0)     # int slot
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 int_const1
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    lw      $a0 12($fp)
    bnez    $a0 dispatch83
    la      $a0 str_const6
    li      $t1 111
    jal     _dispatch_abort
dispatch83:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 20($t1)   # method offset
    jalr    $t1
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    move    $a0 $s0
    bnez    $a0 dispatch84
    la      $a0 str_const6
    li      $t1 111
    jal     _dispatch_abort
dispatch84:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 24($t1)   # method offset
    jalr    $t1
    jal     Object.copy
    lw      $t1 12($a0)     # int slot
    neg     $t1 $t1
    sw      $t1 12($a0)     # int slot
    b       endif94
else93:
    la      $a0 int_const1
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 int_const0
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    lw      $a0 12($fp)
    bnez    $a0 dispatch85
    la      $a0 str_const6
    li      $t1 112
    jal     _dispatch_abort
dispatch85:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 20($t1)   # method offset
    jalr    $t1
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 str_const20
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq86
    la      $a1 bool_const0
    jal     equality_test
eq86:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 else91
    lw      $a0 12($fp)
    bnez    $a0 dispatch87
    la      $a0 str_const6
    li      $t1 112
    jal     _dispatch_abort
dispatch87:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 12($t1)   # method offset
    jalr    $t1
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 int_const1
    jal     Object.copy
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    lw      $t1 12($t1)     # int slot
    lw      $t2 12($a0)     # int slot
    sub     $t1 $t1 $t2
    sw      $t1 12($a0)     # int slot
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 int_const1
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    lw      $a0 12($fp)
    bnez    $a0 dispatch88
    la      $a0 str_const6
    li      $t1 112
    jal     _dispatch_abort
dispatch88:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 20($t1)   # method offset
    jalr    $t1
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    move    $a0 $s0
    bnez    $a0 dispatch89
    la      $a0 str_const6
    li      $t1 112
    jal     _dispatch_abort
dispatch89:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 24($t1)   # method offset
    jalr    $t1
    b       endif92
else91:
    lw      $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    move    $a0 $s0
    bnez    $a0 dispatch90
    la      $a0 str_const6
    li      $t1 113
    jal     _dispatch_abort
dispatch90:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 24($t1)   # method offset
    jalr    $t1
endif92:
endif94:
endif96:
    lw      $fp 12($sp)
    lw      $s0 8($sp)
    lw      $ra 4($sp)
    addiu   $sp $sp 12
    addiu   $sp $sp 4    # params free
    jr      $ra
A2I.a2i_aux:
    addiu   $sp $sp -12
    sw      $fp 12($sp)
    sw      $s0 8($sp)
    sw      $ra 4($sp)
    addiu   $fp $sp 4
    addiu   $sp $sp -12   # locals alloc
    move    $s0 $a0
    la      $a0 int_const0
    sw      $a0 -4($fp)
    lw      $a0 12($fp)
    bnez    $a0 dispatch97
    la      $a0 str_const6
    li      $t1 124
    jal     _dispatch_abort
dispatch97:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 12($t1)   # method offset
    jalr    $t1
    sw      $a0 -8($fp)
    la      $a0 int_const0
    sw      $a0 -12($fp)
while101:
    lw      $a0 -12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    lw      $a0 -8($fp)
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    lw      $t1 12($t1)     # int slot
    lw      $t2 12($a0)     # int slot
    la      $a0 bool_const1
    blt     $t1 $t2 compare98
    la      $a0 bool_const0
compare98:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 endwhile102
    lw      $a0 -4($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 int_const10
    jal     Object.copy
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    lw      $t1 12($t1)     # int slot
    lw      $t2 12($a0)     # int slot
    mul     $t1 $t1 $t2
    sw      $t1 12($a0)     # int slot
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 int_const1
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    lw      $a0 -12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    lw      $a0 12($fp)
    bnez    $a0 dispatch99
    la      $a0 str_const6
    li      $t1 128
    jal     _dispatch_abort
dispatch99:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 20($t1)   # method offset
    jalr    $t1
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    move    $a0 $s0
    bnez    $a0 dispatch100
    la      $a0 str_const6
    li      $t1 128
    jal     _dispatch_abort
dispatch100:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 12($t1)   # method offset
    jalr    $t1
    jal     Object.copy
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    lw      $t1 12($t1)     # int slot
    lw      $t2 12($a0)     # int slot
    add     $t1 $t1 $t2
    sw      $t1 12($a0)     # int slot
    sw      $a0 -4($fp)
    lw      $a0 -12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 int_const1
    jal     Object.copy
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    lw      $t1 12($t1)     # int slot
    lw      $t2 12($a0)     # int slot
    add     $t1 $t1 $t2
    sw      $t1 12($a0)     # int slot
    sw      $a0 -12($fp)
    b       while101
endwhile102:
    move    $a0 $zero
    lw      $a0 -4($fp)
    addiu   $sp $sp 12    # locals free
    lw      $fp 12($sp)
    lw      $s0 8($sp)
    lw      $ra 4($sp)
    addiu   $sp $sp 12
    addiu   $sp $sp 4    # params free
    jr      $ra
A2I.i2a:
    addiu   $sp $sp -12
    sw      $fp 12($sp)
    sw      $s0 8($sp)
    sw      $ra 4($sp)
    addiu   $fp $sp 4
    move    $s0 $a0
    lw      $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 int_const0
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq103
    la      $a1 bool_const0
    jal     equality_test
eq103:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 else110
    la      $a0 str_const9
    b       endif111
else110:
    la      $a0 int_const0
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    lw      $a0 12($fp)
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    lw      $t1 12($t1)     # int slot
    lw      $t2 12($a0)     # int slot
    la      $a0 bool_const1
    blt     $t1 $t2 compare104
    la      $a0 bool_const0
compare104:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 else108
    lw      $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    move    $a0 $s0
    bnez    $a0 dispatch105
    la      $a0 str_const6
    li      $t1 145
    jal     _dispatch_abort
dispatch105:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 32($t1)   # method offset
    jalr    $t1
    b       endif109
else108:
    lw      $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 int_const1
    jal     Object.copy
    lw      $t1 12($a0)     # int slot
    neg     $t1 $t1
    sw      $t1 12($a0)     # int slot
    jal     Object.copy
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    lw      $t1 12($t1)     # int slot
    lw      $t2 12($a0)     # int slot
    mul     $t1 $t1 $t2
    sw      $t1 12($a0)     # int slot
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    move    $a0 $s0
    bnez    $a0 dispatch106
    la      $a0 str_const6
    li      $t1 146
    jal     _dispatch_abort
dispatch106:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 32($t1)   # method offset
    jalr    $t1
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 str_const19
    bnez    $a0 dispatch107
    la      $a0 str_const6
    li      $t1 146
    jal     _dispatch_abort
dispatch107:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 16($t1)   # method offset
    jalr    $t1
endif109:
endif111:
    lw      $fp 12($sp)
    lw      $s0 8($sp)
    lw      $ra 4($sp)
    addiu   $sp $sp 12
    addiu   $sp $sp 4    # params free
    jr      $ra
A2I.i2a_aux:
    addiu   $sp $sp -12
    sw      $fp 12($sp)
    sw      $s0 8($sp)
    sw      $ra 4($sp)
    addiu   $fp $sp 4
    addiu   $sp $sp -4   # locals alloc
    move    $s0 $a0
    lw      $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 int_const0
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    move    $t2 $a0
    la      $a0 bool_const1
    beq     $t1 $t2 eq112
    la      $a1 bool_const0
    jal     equality_test
eq112:
    lw      $t1 12($a0)     # bool slot
    beqz    $t1 else116
    la      $a0 str_const0
    b       endif117
else116:
    lw      $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 int_const10
    jal     Object.copy
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    lw      $t1 12($t1)     # int slot
    lw      $t2 12($a0)     # int slot
    div     $t1 $t1 $t2
    sw      $t1 12($a0)     # int slot
    sw      $a0 -4($fp)
    lw      $a0 12($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    lw      $a0 -4($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    la      $a0 int_const10
    jal     Object.copy
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    lw      $t1 12($t1)     # int slot
    lw      $t2 12($a0)     # int slot
    mul     $t1 $t1 $t2
    sw      $t1 12($a0)     # int slot
    jal     Object.copy
    lw      $t1 4($sp)
    addiu   $sp $sp 4
    lw      $t1 12($t1)     # int slot
    lw      $t2 12($a0)     # int slot
    sub     $t1 $t1 $t2
    sw      $t1 12($a0)     # int slot
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    move    $a0 $s0
    bnez    $a0 dispatch113
    la      $a0 str_const6
    li      $t1 156
    jal     _dispatch_abort
dispatch113:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 16($t1)   # method offset
    jalr    $t1
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    lw      $a0 -4($fp)
    sw      $a0 0($sp)
    addiu   $sp $sp -4
    move    $a0 $s0
    bnez    $a0 dispatch114
    la      $a0 str_const6
    li      $t1 156
    jal     _dispatch_abort
dispatch114:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 32($t1)   # method offset
    jalr    $t1
    bnez    $a0 dispatch115
    la      $a0 str_const6
    li      $t1 156
    jal     _dispatch_abort
dispatch115:
    lw      $t1 8($a0)   # dispatch table
    lw      $t1 16($t1)   # method offset
    jalr    $t1
endif117:
    addiu   $sp $sp 4    # locals free
    lw      $fp 12($sp)
    lw      $s0 8($sp)
    lw      $ra 4($sp)
    addiu   $sp $sp 12
    addiu   $sp $sp 4    # params free
    jr      $ra
