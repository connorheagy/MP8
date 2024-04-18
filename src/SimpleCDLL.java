import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/***
 * Simple circular doubly-linked lists that support the Fail Fast policy.
 * @author Connor Heagy
 * @author Sam R. (base template taken from linked-list lab)
 * 
 */

public class SimpleCDLL<T> implements SimpleList<T> {

  // +--------+------------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * dummy header node
   */
  private Node2<T> dummy;

  /**
   * The number of values in the list.
   */
  int size;

  /**
   * Keeps track of the amount of changes in the indiviual iterator
   */
  int totalCount = 0;

  // +--------------+------------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create an empty list.
   */
  public SimpleCDLL() {
    this.dummy = new Node2<T>(null);
    this.dummy.next = null;
    this.dummy.prev = null;
    this.size = 0;
  } // SimpleDLL

  // +-----------+---------------------------------------------------------
  // | Iterators |
  // +-----------+

  public Iterator<T> iterator() {
    return listIterator(); 
  }

  public ListIterator<T> listIterator() {
    return new ListIterator<T>() {
    
  // +--------+--------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The position in the list of the next value to be returned.
   * Included because ListIterators must provide nextIndex and
   * prevIndex.
   */
    int pos = 0;

  /**
   * The cursor is between neighboring values, so we start links
   * to the previous and next value..
   */
    Node2<T> prev = SimpleCDLL.this.dummy.prev;
    Node2<T> next = SimpleCDLL.this.dummy.next;

  /**
   * The node to be updated by remove or set.  Has a value of
   * null when there is no such value.
   */
    Node2<T> update = null;
  
  /**
   * Keeps track of the total amount of changes in the iterators
   */
    int iteratorCount = totalCount;

  // +---------+-------------------------------------------------------
  // | Methods |
  // +---------+

  public void failFast() throws ConcurrentModificationException {
    if (SimpleCDLL.this.totalCount != this.iteratorCount) {
      throw new ConcurrentModificationException();
    } // if
  } // failFast()

  public void add(T val) throws UnsupportedOperationException {
    // check that the changes in the iterators
    failFast();
    // if the list is empty
    if (dummy.next == null) {
      Node2<T> newNode = new Node2<T>(val);
      dummy.next = newNode;
      dummy.prev = newNode;
      newNode.next = dummy;
      newNode.prev = dummy;
      this.prev = dummy;
      this.next = dummy;
    }
    // normal case
    else {
      this.prev = this.next.insertBefore(val);
    } // else
    // Note that we cannot update
    this.update = null;

    // Increase the size
    ++SimpleCDLL.this.size;

    // Increase the position
    ++this.pos;
    // Increase the total changes made   
    ++SimpleCDLL.this.totalCount;
    // Increase the iterator changes made
    ++this.iteratorCount;
  } // empty list 

  public boolean hasNext() {
    // check that the changes in the iterators
    failFast();
    return (this.pos < SimpleCDLL.this.size);
  } // hasNext()

  public boolean hasPrevious() {
    // check that the changes in the iterators
    failFast();
    return (this.pos > 0);
  } // hasPrevious()

  public T next() {
    // check that the changes in the iterators
    failFast();
    if (!this.hasNext()) {
      throw new NoSuchElementException();
    } // if
    // Identify the node to update
    this.update = this.next;
    // Advance the cursor
    this.prev = this.next;
    this.next = this.next.next;
    // Note the movement
    ++this.pos;
    // And return the value
    return this.update.value;
  } // next()

  public int nextIndex() {
    // check that the changes in the iterators
    failFast();
    return this.pos;
  } // nextIndex()

  public int previousIndex() {
    // check that the changes in the iterators
    failFast();
    return this.pos - 1;
  } // prevIndex

  public T previous() throws NoSuchElementException {
    // check that the changes in the iterators are not different
    failFast();
    if (!this.hasPrevious()) {
      throw new NoSuchElementException();
    } // if
    // identify the node to update
    this.update = this.prev;
    // advance the cursor
    this.next = this.prev;
    this.prev = this.prev.prev;
    // note the movement
    --this.pos;
    // return the value
    return this.update.value;
  }

  public void remove() throws ConcurrentModificationException {
    // check that the changes in the iterators are not different
    failFast();
    // Sanity check
    if (this.update == null) {
      throw new IllegalStateException();
    } // if
  
    // Update the cursor
    if (this.next == this.update) {
      this.next = this.update.next;
    } // if
    if (this.prev == this.update) {
      this.prev = this.update.prev;
      --this.pos;
    } // if
  
    // Update the front
    if (dummy.next == this.update) {
      dummy.next = this.update.next;
    } // if
  
    // Do the real work
    this.update.remove();
    --SimpleCDLL.this.size;
  
    // Note that no more updates are possible
    this.update = null;
    // Increase the total changes made
    ++SimpleCDLL.this.totalCount;
    // Increase the iterator counter
    ++this.iteratorCount;
  } // remove(val)

  public void set(T val) {
    // check that the changes in the iterators
    failFast();
    // Sanity check
    if (this.update == null) {
      throw new IllegalStateException();
    } // if
    // Do the real work
    this.update.value = val;
    // Note that no more updates are possible
    this.update = null;
  } // set(val)
}; // ListIterator<T>()
} // listIterator()
} // class SimpleCDLL
