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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.vm.op.AbstractTreeNode;
import xyz.cofe.lang2.vm.op.DebugWrapperValue;
import xyz.cofe.lang2.vm.op.Function;
import xyz.cofe.lang2.vm.op.Variable;
import xyz.cofe.lang2.vm.op.VariableDeffine;
import xyz.cofe.collection.iterators.TreeWalk;

/**
 * Фкнкция определяющпая внешние переменные
 * @author gocha
 */
public class VrFunction extends Function implements Callable
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(VrFunction.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(VrFunction.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(VrFunction.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(VrFunction.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(VrFunction.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(VrFunction.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /**
     * Констркутор по умолчанию
     */
    public VrFunction(){
    }
    
    /**
     * Конструктор копирования
     * @param src Исходный объект
     * @param deep Полное копирование
     */
    public VrFunction(Function src,boolean deep){
        super(src, deep);
        if( src instanceof VrFunction ){
            externalVars.putAll( ((VrFunction)src).externalVars );
        }
    }
    
    /**
     * Полное копирование
     * @return полняа копия
     */
    @Override
    public Value deepClone() {
        return new VrFunction(this, true);
    }
    
    protected Map<String,Object> externalVars = new HashMap<String,Object>();
    
    /**
     * ПРоверяет наличаеи внешней переменной
     * @param name Имя переменной
     * @return есть в списке
     */
    public boolean hasExtVar(String name){
        if( name==null )return false;
        if( externalVars.containsKey(name) )return true;
        return false;
    }
    
    /**
     * Добавляет внешнюю переменную
     * @param name Имя перменной
     */
    public void addExtVar(String name){
        if( name==null )return;
        if( externalVars.containsKey(name) )return;
        logFine("addExtVar( {0} )", name);
        externalVars.put(name, null);
    }
    
    /**
     * Удаляет внешнуюю переменную
     * @param name Переменная
     */
    public void removeExtVar(String name){
        if( name==null )return;
        externalVars.remove(name);
        logFine("removeExtVar( {0} )", name);
    }
    
    /**
     * Возвращает список внешних переменных для указанной функции
     * @return Список переменных
     */
    public String[] getExtVars(){
        return externalVars.keySet().toArray(new String[]{});
    }
    
    /**
     * Устанавлиает список внешних переменных
     * @param args Список переменных
     */
    public void setExtVars(String[] args){
        externalVars.clear();
        for( String a : args ){
            if( a!=null )externalVars.put(a, null);
        }
    }

    /* (non-Javadoc) @see Function */
    @Override
    public Object call(Object... arguments) {
        return super.call(arguments);
    }

    /* (non-Javadoc) @see Function */
    @Override
    protected void createSpecialVars(Object... arguments) {
        if( memory!=null ){
            for( Map.Entry<String,Object> e : externalVars.entrySet() ){
                String var = e.getKey();
                Object val = e.getValue();
//                System.out.println("vr create spec "+var+" = "+val);
//                memory.put(var, val);
                VariableDeffine.defineVariable(memory, var, val);
                logFine("createSpecialVars( {0}={1} )", var, val);
            }
        }
        super.createSpecialVars(arguments);
    }
    
    /**
     * Флаг - внешние переменные запонены
     */
    protected boolean extVarSaved = false;

    /**
     * Вычисляет значение функции - запоминает значения внешних перменных
     * во внутренней памяти функции
     */
    @Override
    public Object evaluate() {
        if( !extVarSaved ){
            if( memory!=null ){
                for( String evar : externalVars.keySet() ){
                    if( memory.containsKey(evar) ){
                        Object val = memory.get(evar);
                        externalVars.put(evar, val);
//                        System.out.println("vr eval "+evar+" = "+val);
                    }
                }
            }
            extVarSaved = true;
        }
        return this;
    }

    /**
     * Смена функций на спец.
     * @param l Список функций и переменных
     */
    public static void replaceFunctions(List<ExternalVar> l){
        if( l==null )return;
        
        int lidx = -1;
        logFine("replaceFunctions begin, count={0}",l.size());
        logFiner("replace functions");
        for( ExternalVar ev : l ){
            if( ev==null )continue;
            if( ev.var==null )continue;
            if( ev.var.getVariable()==null )continue;
            if( ev.extFunctions==null )continue;
            if( ev.extFunctions.size()<1 )continue;
            
            lidx++;
            logFiner("idx={0}, extFunctions.size()={1}", lidx, ev.extFunctions.size());
            
            for( int i=0; i<ev.extFunctions.size(); i++ ){
                Function f = ev.extFunctions.get(i);
                logFiner("fun idx={0} fun={1}", i, f);
                
                if( !(f instanceof VrFunction) ){
                    VrFunction vfunc = new VrFunction(f, true);
                    logFiner( "created vrFunction={0}",vfunc );
                    
                    Value fparent = f.getParent();
                    
                    // работа с частным случаем - объеками и debug
                    boolean parentIsDebug = fparent instanceof DebugWrapperValue;
                    if( parentIsDebug ){
                        logFiner( "parent is debug" );
                        
                        fparent = ((DebugWrapperValue)fparent).getParent();
                        Value debugWrapperObj = f.getParent();
                        int idx = debugWrapperObj.getIndex();
                        logFiner( "src function src.debugWrapper.index={0}",idx );
                        
                        if( idx>=0 && fparent!=null ){
                            fparent.setChild(idx, vfunc);
                            logFiner( "parent.setChild( index={0}, vrFunction={1} )",idx,vfunc );
                            
                            ev.extFunctions.set(i, vfunc);
                            logFiner( "ev.extFunctions.set( index={0}, vrFunction={1} )",i,vfunc );
                        }
                    }else{
                        logFiner( "parent is not debug" );
                        
                        if( fparent!=null ){
                            int idx = f.getIndex();
                            logFiner( "src function src.index={0}",idx );
                            if( idx>=0 && fparent!=null ){
                                fparent.setChild(idx, vfunc);
                                logFiner( "parent.setChild( index={0}, vrFunction={1} )",idx,vfunc );
                                
                                ev.extFunctions.set(i, vfunc);
                                logFiner( "ev.extFunctions.set( index={0}, vrFunction={1} )",i,vfunc );
                            }
                        }else{
                            logFiner("parent is null");
                        }
                    }
                }
            }
        }
        
        logFiner("add ext vars names to vrFunctions, count={0}",l.size());
        lidx = -1;
        for( ExternalVar ev : l ){
            if( ev==null )continue;
            if( ev.var==null )continue;
            if( ev.var.getVariable()==null )continue;
            if( ev.extFunctions==null )continue;
            if( ev.extFunctions.size()<1 )continue;
            
            lidx++;
            logFiner("idx={0}, func count={1}",lidx, ev.extFunctions.size());
            
            for( int i=0; i<ev.extFunctions.size(); i++ ){
                Function f = ev.extFunctions.get(i);
                logFiner("idx={0}, func idx={1}, func={2}",lidx, i,f);
                
                if( f instanceof VrFunction ){
                    ((VrFunction)f).addExtVar(ev.var.getVariable());
                    logFiner("idx={0}, func idx={1}, addExtVar( var={2} )",lidx, i,ev.var.getVariable());
                }
            }
        }
        logFine("replaceFunctions end");
    }
    
    /**
     * Описывает внешнюю переменную
     */
    public static class ExternalVar {
        /**
         * Переменная
         */
        public Variable var;
        
        /**
         * Место где она определенна
         */
        public Value defineLocation;
        
        /**
         * Функции ссылающиеся на эту переменную
         */
        public List<Function> extFunctions = new ArrayList<Function>();
    }
    
    /**
     * Фильтр внешних переменных
     * @param varDefs Переменные и места их определения
     * @return Внешние переменные
     */
    public static List<ExternalVar> filterExternalVariables( Map<Variable,Value> varDefs ){
        List<ExternalVar> extVars = new ArrayList<ExternalVar>();
        for( Variable var : varDefs.keySet() ){
            Value defLoc = varDefs.get(var);
            ValuePath defPath = new ValuePath(defLoc);
            ValuePath varPath = new ValuePath(var);
            ValuePath diffPath = varPath.diff(defPath);
            
            int found = 0;
            ExternalVar evar = null;
            for( Value v : diffPath ){
                if( v instanceof Function ){
                    found++;
                    if( found==1 ){
                        evar = new ExternalVar();
                        extVars.add(evar);
                        evar.var = var;
                        evar.defineLocation = defLoc;
                    }
                    evar.extFunctions.add((Function)v);
                    
                    int i=-1;
                    StringBuilder fun = new StringBuilder();
                    for( Function f : evar.extFunctions ){
                        i++;
                        if( i>0 )fun.append(", ");
                        fun.append("fun=");
                        fun.append(new ValuePath(f).toString());
                    }
                    logFiner("filterExternalVariables( var={0} defpath={1} fun={2} )", 
                            evar.var.getVariable(),
                            new ValuePath(evar.defineLocation),
                            fun.toString()
                            );
                }
            }
        }
        logFiner("filterExternalVariables( count={0} )", extVars.size());
        return extVars;
    }
    
    /**
     * Поиск в коде ссылок (lang2.vm.op.Variable) на переменные
     * @param treeRoot Корень относительно которого производить поиск
     * @return Ссылки на переменные
     */
    public static List<Variable> findVariables(Value treeRoot){
        if (treeRoot== null) {            
            throw new IllegalArgumentException("treeRoot==null");
        }
        List<Variable> vars = new ArrayList<Variable>();
        for( TreeWalk<Value> twV : AbstractTreeNode.tree(treeRoot) ){
            Value v = twV.currentNode();
            if( v instanceof Variable ){
                vars.add((Variable)v);
                logFiner("findVariables( var={0} path={1} )", ((Variable)v).getVariable(), new ValuePath(v));
            }
        }
        return vars;
    }

	/**
	 * Поиск места определения переменной
	 * @param vars Список переменных
	 * @return Карта - Переменная / место определения 
	 */
	public static Map<Variable,Value> findVarDefineLocation(List<Variable> vars, boolean ignoreCase){
		Map<Variable,Value> locations = new HashMap<Variable, Value>();
		for( Variable var : vars ){
			Value location = findVarDefineLocation(var, ignoreCase);
			if( location!=null ){
				locations.put(var, location);
                logFiner("findVarDefineLocation( var={0} defpath={1} )", var.getVariable(), new ValuePath(location));
            }
		}
		return locations;
	}

	/**
	 * Поиск места определения переменной
	 * @param var Переменная
	 * @return Место ее определения
	 */
	public static Value findVarDefineLocation(Variable var,boolean ignoreCase){
        //..................
//        Value root = var;
//        while(true){
//            Value p = root.getParent();
//            if( p!=null ){
//                root = p;
//                continue;
//            }
//            break;
//        }
//        System.out.println("findVarDefineLocation( var="+var.getVariable()+" )");
//        System.out.println("var path:"+new ValuePath(var));
//        System.out.println("root:");
//        SyntaxTree.printTree(root);
        //..................
		Value ptr = var;
		String needVarName = var.getVariable();
		if( needVarName==null )return null;
		while(true){
			while(true){
				Value prevCode = ptr.getSibling(-1);
				if( prevCode == null )break;
				ptr = prevCode;
                // ... check ...
                if( ptr instanceof DebugWrapperValue ){
                    Value unwrap = ((DebugWrapperValue)ptr).unwrap();
                    if( matchedDefinedVarName(needVarName, unwrap, ignoreCase) )
                        return unwrap;
                }else{
                    if( matchedDefinedVarName(needVarName, ptr, ignoreCase) )
                        return ptr;
                }
			}
			Value parent = ptr.getParent();
			if( parent==null )break;
			ptr = parent;
            // ... check ...
            if( ptr instanceof DebugWrapperValue ){
                Value unwrap = ((DebugWrapperValue)ptr).unwrap();
                if( matchedDefinedVarName(needVarName, unwrap, ignoreCase) )
                    return unwrap;
            }else{
                if( matchedDefinedVarName(needVarName, ptr, ignoreCase) )
                    return ptr;
            }
		}
		return null;
	}
	
	/**
	 * Проверяет совпадение имени искомой переменной с определяемыми переменными (getNewVaraibleNames)
	 * @param needVarName Искомая переменная
	 * @param v Участоко проверяемого кода
	 * @param ignoreCase Игнорировать регистр в имени переменной
	 * @return Совпадает (true) / Не совпадает (false)
	 */
	public static boolean matchedDefinedVarName(String needVarName,Value v,boolean ignoreCase){
		if( v==null )return false;
//        if( v instanceof DebugWrapperValue ){
//            v = ((DebugWrapperValue)v).unwrap();
//        }
		if( v instanceof NewVariables ){
			String[] newVars = ((NewVariables)v).getNewVaraibleNames();
			if( newVars!=null ){
				for( String newVarName : newVars ){
					if( newVarName==null )continue;
					if( ignoreCase ){
						if( needVarName.equalsIgnoreCase(newVarName) ){
							return true;
						}
					}else{
						if( needVarName.equals(newVarName) ){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
