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
package xyz.cofe.lang2.vm;

/**
 * Описывает примитивынй тип данных
 * @author gocha
 */
public class PrimitiveType implements Type
{
    /**
     * Конструктор
     * @param name имя типа
     */
    protected PrimitiveType(String name){        
        this.name = name;
    }

    /**
     * Конструктор
     * @param name имя типа
     * @param parent базовый тип
     */
    protected PrimitiveType(String name,Type parent){
        this.name = name;
        this.parent = parent;
    }

    protected String name = null;

    /* (non-Javadoc) @see Type */
    @Override
    public String getName() {
        return name;
    }

    protected Type parent = null;

    /* (non-Javadoc) @see Type */
    @Override
    public Type getParent(){
        return parent;
    }

    /**
     * Булево
     */
    public static final PrimitiveType Bool = new PrimitiveType("bool");
    
    /**
     * Строка
     */
    public static final PrimitiveType String = new PrimitiveType("string");
    
    /**
     * Не обпределенный тип данных
     */
    public static final PrimitiveType Undefined = new PrimitiveType("undefined");
    
    /**
     * Тип NULL - Соответ. нулевой ссылке
     */
    public static final PrimitiveType Null = new PrimitiveType("null");

    /**
     * Тип Integer - 32 битное, рациональное число
     */
    public static final PrimitiveType Float = new PrimitiveType("float");
    
    /**
     * Тип Integer - 64 битное, рациональное число
     */
    public static final PrimitiveType Double = new PrimitiveType("double");

    /**
     * Тип Integer - 64 битное, целое число
     */
    public static final PrimitiveType Long = new PrimitiveType("long");
    
    /**
     * Тип Integer - 32 битное, целое число
     */
    public static final PrimitiveType Integer = new PrimitiveType("int");

    /**
     * Тип Integer - 8 битное, целое число
     */
    public static final PrimitiveType Byte = new PrimitiveType("byte");

    /**
     * Тип Integer - 16 битное, целое число
     */
    public static final PrimitiveType Short = new PrimitiveType("short");
}
