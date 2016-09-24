grammar Roxy;

prog
    :    expr EOF
    ;

expr
    :    '(' expr ')'
    |    left=expr op=('*'|'/') right=expr
    |    left=expr op=('+'|'-') right=expr
    |    value=NUM
    ;

OP_ADD: '+';
OP_SUB: '-';
OP_MUL: '*';
OP_DIV: '/';

NUM :   [0-9]+ ('.' [0-9]+)?;
WS  :   [ \t\r\n] -> channel(HIDDEN);    
