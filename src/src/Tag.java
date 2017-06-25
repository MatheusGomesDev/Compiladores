/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

/**
 *
 * @author matheus
 */
public enum Tag {
    
    // fim de arquivo
    EOF,
    
    //final de expressão
    FinalExpressao,
    
    //dois pontos
    DoisPontos,
    
    //ponto
    Ponto,
    PontoVirgula,
    
    //virgula
    Virgula,
    
    //demilitadores
    Demilitador,
    DemilitadorFuncao,
    
    //Operadores
    OpAtribuicao,
    OpMaiorQue,
    OpMaiorQueIgual,
    OpMenorQue,
    OpMenorQueIgual,
    OpIgual,
    OpDiferente,
    OpSoma,
    OpSubtracao,
    OpMultiplicacao,
    OpDivisao,
    OpUnarioNegativo,
    OpUnarioNegacao,
    
    //identificador
    ID,
 
    //string
    ConstString,
        
    //numeros
    ConstInteger,
    ConstDouble,
    
    //Comentario
    Comentario,
    
    //Parenteses
    AbreParenteses,
    FechaParenteses,
    
    //Colchetes
    AbreColchetes,
    FechaColchetes,
    
    // palavra reservada
    KW,
    KW_compClass,
    KW_compEnd,
    KW_compDef,
    KW_compReturn,
    KW_compDefstatic,
    KW_compVoid,
    KW_compMain,
    KW_compBool,
    KW_compAnd,
    KW_compOr,
    KW_compInteger,
    KW_compString,
    KW_compDouble,
    KW_compIf,
    KW_compElse,
    KW_compWhile,
    KW_compWrite,
    KW_compWriteln,
    KW_compTrue,
    KW_compFalse,
    KW_compVector,
    

    
    
    

}
