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
package xyz.cofe.lang2.parser;

import java.util.ArrayList;
import java.util.List;
import org.antlr.runtime.CharStream;

/**
 * Поток символов
 * @author gocha
 */
public class StringStream implements CharStream {
    public static class CharStreamState {
        /** Index into the char stream of next lookahead char */
        int p;

        /** What line number is the scanner at before processing buffer[p]? */
        int line;

        /** What char position 0..n-1 in line is scanner before processing buffer[p]? */
        int charPositionInLine;
    }

	/** The data being scanned */
	protected char[] data;

	/** How many characters are actually in the buffer */
	protected int n;

	/** 0..n-1 index into string of next char */
	protected int p=0;

	/** line number 1..n within the input */
	protected int line = 1;

	/** The index of the character relative to the beginning of the line 0..n-1 */
	protected int charPositionInLine = 0;

	/** tracks how deep mark() calls are nested */
	protected int markDepth = 0;

	/** A list of CharStreamState objects that tracks the stream state
	 *  values line, charPositionInLine, and p that can change as you
	 *  move through the input stream.  Indexed from 1..markDepth.
     *  A null is kept @ index 0.  Create upon first call to mark().
	 */
	protected List markers;

	/** Track the last mark() call result value for use in rewind(). */
	protected int lastMarker;

	/** What is name or source of this char stream? */
	public String name;

	public StringStream() {
	}

	/** Copy data in string to a local char array */
	public StringStream(String input) {
		this();
		this.data = input.toCharArray();
		this.n = input.length();
	}

	/** This is the preferred constructor as no data is copied */
	public StringStream(char[] data, int numberOfActualCharsInArray) {
		this();
		this.data = data;
		this.n = numberOfActualCharsInArray;
	}

    public char getLineDelimeter(){ return '\n'; }
    public char[] getData(){ return data; }
    public int getDataLen(){ return n; }

    public String getDataAsString(){
        return new String(data,0,n);
    }

	/** Reset the stream so that it's in the same state it was
	 *  when the object was created *except* the data array is not
	 *  touched.
	 */
	public void reset() {
		p = 0;
		line = 1;
		charPositionInLine = 0;
		markDepth = 0;
	}

    @Override
    public void consume() {
		//System.out.println("prev p="+p+", c="+(char)data[p]);
        if ( p < n ) {
			charPositionInLine++;
			if ( data[p]==getLineDelimeter() ) {
				/*
				System.out.println("newline char found on line: "+line+
								   "@ pos="+charPositionInLine);
				*/
				line++;
				charPositionInLine=0;
			}
            p++;
			//System.out.println("p moves to "+p+" (c='"+(char)data[p]+"')");
        }
    }

    @Override
    public int LA(int i) {
		if ( i==0 ) {
			return 0; // undefined
		}
		if ( i<0 ) {
			i++; // e.g., translate LA(-1) to use offset i=0; then data[p+0-1]
			if ( (p+i-1) < 0 ) {
				return CharStream.EOF; // invalid; no char before first char
			}
		}

		if ( (p+i-1) >= n ) {
            //System.out.println("char LA("+i+")=EOF; p="+p);
            return CharStream.EOF;
        }
        //System.out.println("char LA("+i+")="+(char)data[p+i-1]+"; p="+p);
		//System.out.println("LA("+i+"); p="+p+" n="+n+" data.length="+data.length);
		return data[p+i-1];
    }

    @Override
	public int LT(int i) {
		return LA(i);
	}

	/** Return the current input symbol index 0..n where n indicates the
     *  last symbol has been read.  The index is the index of char to
	 *  be returned from LA(1).
     */
    @Override
    public int index() {
        return p;
    }

    @Override
	public int size() {
		return n;
	}

    @Override
	public int mark() {
        if ( markers==null ) {
            markers = new ArrayList();
            markers.add(null); // depth 0 means no backtracking, leave blank
        }
        markDepth++;
		CharStreamState state = null;
		if ( markDepth>=markers.size() ) {
			state = new CharStreamState();
			markers.add(state);
		}
		else {
			state = (CharStreamState)markers.get(markDepth);
		}
		state.p = p;
		state.line = line;
		state.charPositionInLine = charPositionInLine;
		lastMarker = markDepth;
		return markDepth;
    }

    @Override
    public void rewind(int m) {
		CharStreamState state = (CharStreamState)markers.get(m);
		// restore stream state
		seek(state.p);
		line = state.line;
		charPositionInLine = state.charPositionInLine;
		release(m);
	}

    @Override
	public void rewind() {
		rewind(lastMarker);
	}

    @Override
	public void release(int marker) {
		// unwind any other markers made after m and release m
		markDepth = marker;
		// release this marker
		markDepth--;
	}

	/** consume() ahead until p==index; can't just set p=index as we must
	 *  update line and charPositionInLine.
	 */
    @Override
	public void seek(int index) {
		if ( index<=p ) {
			p = index; // just jump; don't update stream state (line, ...)
			return;
		}
		// seek forward, consume until p hits index
		while ( p<index ) {
			consume();
		}
	}

    @Override
	public String substring(int start, int stop) {
		return new String(data,start,stop-start+1);
	}

    @Override
	public int getLine() {
		return line;
	}

    @Override
	public int getCharPositionInLine() {
		return charPositionInLine;
	}

    @Override
	public void setLine(int line) {
		this.line = line;
	}

    @Override
	public void setCharPositionInLine(int pos) {
		this.charPositionInLine = pos;
	}

    @Override
	public String getSourceName() {
		return name;
	}
}
