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
package xyz.cofe.lang2.vm.op;

/**
 * Имена операторов 
 * @author Камнев Георгий Павлович
 */
public enum OperatorName {
	/**
	 * Логическая конструкция NOT
	 */
	NOT,
	
	/**
	 * Умножение числа a на b
	 */
	MULTIPLE,
	
	/**
	 * Деление числа a на b
	 */
	DIVIDE,
    
    /**
     * Остаток от цело численного диления
     */
    MOD,
	
	/**
	 * Унарный минус
	 */
	UNARY_MINUS,
	
	/**
	 * Сложение чисел/строк a + b
	 */
	ADD,
	
	/**
	 * Вычистание чисел a - b
	 */
	SUBSTRACT,
	
	/**
	 * Сравнение велечин a и b на равенство
	 */
	COMAPRE_EQUALS,
	
	/**
	 * Сравнение велечин a и b на не равенство
	 */
	COMAPRE_NOT_EQUALS,
	
	/**
	 * Сравнение велечин a и b на больше
	 */
	COMAPRE_MORE,
	
	/**
	 * Сравнение велечин a и b на меньше
	 */
	COMAPRE_LESS,
	
	/**
	 * Сравнение велечин a и b на больше или равное
	 */
	COMAPRE_MORE_OR_EQUALS,
	
	/**
	 * Сравнение велечин a и b на меньше или равное
	 */
	COMAPRE_LESS_OR_EQUALS,
	
	/**
	 * Операция логического <b>a</b> И <b>b</b>
	 */
	AND,
	
	/**
	 * Операция логического <b>a</b> НЕ ИЛИ <b>b</b>
	 */
	XOR,
	
	/**
	 * Операция логического <b>a</b> ИЛИ <b>b</b>
	 */
	OR,
	
	/**
	 * Устанавливает значение <b>variable<i>(lvalue)</i> = value</b>
	 */
	ASSIGN
}
