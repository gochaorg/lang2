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

import java.util.Iterator;
import java.util.Map;
import xyz.cofe.lang2.vm.err.RuntimeError;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.collection.BasicPair;
import xyz.cofe.collection.Pair;

/**
 * Расширение объекта String<br/><br/>
 * 
 * Для строки можно обращаться по индексу: <i><b>строка[индекс]</b></i> <br/>
 * В качестве индекса может быть задано число или объект.<br/>
 * Объект должен обладать двумя полями <b>begin</b> и <b>end</b> - тогда вернет строку начиная с указанного символа (begin)
 * и до символа end (исключительно).<br/>
 * Либо объект может содержать поля <b>begin</b> и <b>length</b> - тогда будет возвращена строка с указанного символа и 
 * указанной длины.<br/><br/>
 * Пример:<br/>
 * <font face="monospace">
 * l2> var a = "abcdefghijklmnop" <br/>
 * "abcdefghijklmnop" <br/><br/>
 * 
 * l2> a.length <br/>
 * 16 <br/><br/>
 * 
 * l2> a[0] <br/>
 * "a" <br/><br/>
 * 
 * l2> a[ {begin:1, end: 3} ] <br/>
 * "bc" <br/><br/>
 * 
 * l2> a[ {begin:1, length: 2} ] <br/>
 * "bc" <br/><br/>
 * 
 * l2> a[-1] <br/>
 * "p" <br/><br/>
 * 
 * l2> a[-16] <br/>
 * "a" <br/><br/>
 * 
 * l2> a[-18] <br/>
 * "" <br/><br/>
 * 
 * l2> a[20] <br/>
 * "" <br/><br/>
 * 
 * </font>
 * @author gocha
 */
public class StringExtender extends BasicExtender {
    @Override
    public Pair<Object, Boolean> extendArrayIndex(Value invoker, Object base, Object index) {
        if( index instanceof Number ){
            if( base instanceof String ){
                int charidx = ((Number)index).intValue();
                String str = (String)base;
                if( charidx>=str.length() ){
                    return result("");
                }
                if( charidx>=0 ){
                    return result(new String(new char[]{str.charAt(charidx)}));
                }
                if( charidx<0 ){
                    int cidx = -charidx;
                    if( cidx>str.length() ){
                        return result("");
                    }
                    return result(new String(new char[]{str.charAt(str.length() - cidx)}));
                }
            }
        }
        if( index instanceof Map ){
            Map map = (Map)index;
            if( base instanceof String ){
                if( map.containsKey("begin") && map.containsKey("end") ){
                    Object obegin = map.get("begin");
                    Object oend = map.get("end");

                    if( obegin instanceof Number && oend instanceof Number ){
                        int begin = ((Number)obegin).intValue();
                        int end = ((Number)oend).intValue();

                        String str = (String)base;
                        try{
                            Object res = str.substring(begin, end);
                            return result(res);
                        }catch(IndexOutOfBoundsException e){
                            throw new RuntimeError(invoker, "IndexOutOfBoundsException", e);
                        }
                    }
                }else if( map.containsKey("begin") && map.containsKey("length") ){
                    Object obegin = map.get("begin");
                    Object olength = map.get("length");

                    if( obegin instanceof Number && olength instanceof Number ){
                        int begin = ((Number)obegin).intValue();
                        int length = ((Number)olength).intValue();
                        int end = begin + length;

                        String str = (String)base;
                        try{
                            Object res = str.substring(begin, end);
                            return result(res);
                        }catch(IndexOutOfBoundsException e){
                            throw new RuntimeError(invoker, "IndexOutOfBoundsException", e);
                        }
                    }
                }else if( map.containsKey("begin") ){
                    Object obegin = map.get("begin");

                    if( obegin instanceof Number ){
                        int begin = ((Number)obegin).intValue();

                        String str = (String)base;
                        try{
                            Object res = str.substring(begin);
                            return result(res);
                        }catch(IndexOutOfBoundsException e){
                            throw new RuntimeError(invoker, "IndexOutOfBoundsException", e);
                        }
                    }
                }
            }
        }
        
        return faild;
    }

    @Override
    public Pair<Iterator, Boolean> extendFor(Value invoker, Object extendedObject) {
        if( extendedObject instanceof String ){
            final String str = (String)extendedObject;
            Iterator itr = new Iterator() {
                protected int idx = 0;

                @Override
                public boolean hasNext() {
                    if( str.length()==0 )return false;
                    return idx < str.length();
                }

                @Override
                public Object next() {
                    char c = str.charAt(idx);
                    idx++;
                    String s = new String(new char[]{c});
                    return s;
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            };
            return new BasicPair<Iterator, Boolean>( itr, true );
        }
        return faildForExtend;
    }
}
