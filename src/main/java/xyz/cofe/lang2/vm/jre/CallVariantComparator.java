/* 
 * The MIT License
 *
 * Copyright 2014 Kamnev Georgiy (nt.gocha@gmail.com).
 *
 * Данная лицензия разрешает, безвозмездно, лицам, получившим копию данного программного 
 * обеспечения и сопутствующей документации (в дальнейшем именуемыми "Программное Обеспечение"), 
 * использовать Программное Обеспечение без ограничений, включая неограниченное право на 
 * использование, копирование, изменение, объединение, публикацию, распространение, сублицензирование 
 * и/или продажу копий Программного Обеспечения, также как и лицам, которым предоставляется 
 * данное Программное Обеспечение, при соблюдении следующих условий:
 *
 * Вышеупомянутый копирайт и данные условия должны быть включены во все копии 
 * или значимые части данного Программного Обеспечения.
 *
 * ДАННОЕ ПРОГРАММНОЕ ОБЕСПЕЧЕНИЕ ПРЕДОСТАВЛЯЕТСЯ «КАК ЕСТЬ», БЕЗ ЛЮБОГО ВИДА ГАРАНТИЙ, 
 * ЯВНО ВЫРАЖЕННЫХ ИЛИ ПОДРАЗУМЕВАЕМЫХ, ВКЛЮЧАЯ, НО НЕ ОГРАНИЧИВАЯСЬ ГАРАНТИЯМИ ТОВАРНОЙ ПРИГОДНОСТИ, 
 * СООТВЕТСТВИЯ ПО ЕГО КОНКРЕТНОМУ НАЗНАЧЕНИЮ И НЕНАРУШЕНИЯ ПРАВ. НИ В КАКОМ СЛУЧАЕ АВТОРЫ 
 * ИЛИ ПРАВООБЛАДАТЕЛИ НЕ НЕСУТ ОТВЕТСТВЕННОСТИ ПО ИСКАМ О ВОЗМЕЩЕНИИ УЩЕРБА, УБЫТКОВ 
 * ИЛИ ДРУГИХ ТРЕБОВАНИЙ ПО ДЕЙСТВУЮЩИМ КОНТРАКТАМ, ДЕЛИКТАМ ИЛИ ИНОМУ, ВОЗНИКШИМ ИЗ, ИМЕЮЩИМ 
 * ПРИЧИНОЙ ИЛИ СВЯЗАННЫМ С ПРОГРАММНЫМ ОБЕСПЕЧЕНИЕМ ИЛИ ИСПОЛЬЗОВАНИЕМ ПРОГРАММНОГО ОБЕСПЕЧЕНИЯ 
 * ИЛИ ИНЫМИ ДЕЙСТВИЯМИ С ПРОГРАММНЫМ ОБЕСПЕЧЕНИЕМ.
 */
package xyz.cofe.lang2.vm.jre;


import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Сравнивает варианты запуска:<br/>
 * @author gocha
 */
public class CallVariantComparator  implements java.util.Comparator<CallVariant> {
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(CallVariantComparator.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(CallVariantComparator.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(CallVariantComparator.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(CallVariantComparator.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(CallVariantComparator.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(CallVariantComparator.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    @Override
    public int compare(CallVariant o1, CallVariant o2) {
        logFine("compare call variant a={0} b={1}", o1, o2);

        int w1 = o1.getArgs().getSummaryWeight();
        int w2 = o2.getArgs().getSummaryWeight();
        logFine("cast weight a={0} b={1}",w1,w2);

        Object[] src1 = o1.getSourceArgs();
        Object[] src2 = o2.getSourceArgs();

        int callArgsLen1 = o1.getArgs().getCallableArguments().length;
        int callArgsLen2 = o2.getArgs().getCallableArguments().length;
        logFine("args len a={0} b={1}",callArgsLen1,callArgsLen2);

        int srcArgLen1 = src1==null ? 0 : src1.length;
        int arcArgLen2 = src2==null ? 0 : src2.length;

        int diff_sourceArgs_callArg_len_1 = srcArgLen1>callArgsLen1 ? srcArgLen1 - callArgsLen1 : callArgsLen1 - srcArgLen1;
        int diff_sourceArgs_callArg_len_2 = arcArgLen2>callArgsLen2 ? arcArgLen2 - callArgsLen2 : callArgsLen2 - arcArgLen2;
        logFine("diff args len a={0} b={1}",diff_sourceArgs_callArg_len_1,diff_sourceArgs_callArg_len_2);

        int cmp_weight = w1==w2 ? 0 : (w1>w2 ? 1 : -1);
        logFine("cmp_weight = {0}",cmp_weight);

        int cmp_diff_sourceArg_callArg_len = diff_sourceArgs_callArg_len_1==diff_sourceArgs_callArg_len_2 ?
                0 : (diff_sourceArgs_callArg_len_1>diff_sourceArgs_callArg_len_2 ? 1 : -1);
        logFine("cmp_diff_sourceArg_callArg_len = {0}",cmp_weight);

        if( cmp_diff_sourceArg_callArg_len!=0 )return cmp_diff_sourceArg_callArg_len;

        logFine("use cmp_weight = {0}",cmp_weight);
        return cmp_weight;
    }
}
