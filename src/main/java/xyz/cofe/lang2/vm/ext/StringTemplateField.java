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
package xyz.cofe.lang2.vm.ext;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.vm.Callable;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.common.Wrapper;
import xyz.cofe.collection.Convertor;
import xyz.cofe.common.Text;

/**
 * Расширение java.lang.String - поле template возвращающее соответ метод
 * @author nt.gocha@gmail.com
 * @see org.gocha.common.Text#template(java.lang.String, java.lang.Object[]) 
 */
public class StringTemplateField implements Convertor {
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(StringTemplateField.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(StringTemplateField.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(StringTemplateField.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(StringTemplateField.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(StringTemplateField.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(StringTemplateField.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    public static Callable template(final String string){
        return new Callable() {
            @Override
            public Object call(Object... arguments) {
                if( string!=null && arguments!=null ){
                    String res = null;
                    if( arguments[0] instanceof Map ){
                        Map<String,String> sm = new HashMap<String, String>();
                        Map m = (Map)arguments[0];
                        for( Object _e : m.entrySet() ){
                            if( !(_e instanceof Map.Entry) )continue;
                            Map.Entry e = (Map.Entry)_e;
                            Object _k = e.getKey();
                            Object _v = e.getValue();
                            if( _k instanceof Wrapper )
                                _k = ((Wrapper)_k).unwrap();
                            if( _v instanceof Wrapper )
                                _v = ((Wrapper)_v).unwrap();
                            if( _k instanceof Value )
                                _k = ((Value)_k).evaluate();
                            if( _v instanceof Value )
                                _v = ((Value)_v).evaluate();
                            if( _k==null )continue;
                            sm.put(_k.toString(), _v==null ? "null" : _v.toString());
                        }
                        res = Text.template(string, sm);
                    }else{
                        res = Text.template(string, arguments);
                    }
                    return res;
                }
                return string;
            }
        };
    }
    
    @Override
    public Object convert(Object from) {
        if( !(from instanceof String) )throw new ClassCastException(
                from!=null ? (from.getClass().getName() + " to String") :
                "null to String"
                );
        String str = (String)from;
        return template(str);
    }
}
