package impl;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import common.InvalidIndexException;
import common.ListNode;
import interfaces.IListManipulator;
import interfaces.IOperator;
import interfaces.ITransformation;
import org.w3c.dom.ls.LSException;

/**
 * This class represents the recursive implementation of the IListManipulator interface.
 *
 */
public class RecursiveListManipulator implements IListManipulator {

    @Override
    public int size(ListNode head) {
        int size = 0;
        if (head==null){
            return size;
        }
        else {
            return size(head.next)+1;
        }
    }

    @Override
    public boolean contains(ListNode head, Object element) {
        if(head==null){
            return false;
        }
        else if (head.element.equals(element)){
            return true;
        }
        else return contains(head.next, element);
    }

    @Override
    public int count(ListNode head, Object element) {
        int count = 0;
        if (head==null){
            return count;
        }
        else {
            if (head.element.equals(element)){
                return count(head.next,element)+1;
            }
            else{
                return count(head.next,element);
            }
        }
    }

    @Override
    public String convertToString(ListNode head) {
        if (head==null){
            return "";
        }
        if (head.next==null){
            return head.element.toString();
        }
        else{
            return head.element+","+convertToString(head.next);
        }
    }

    @Override
    public Object getFromFront(ListNode head, int n) throws InvalidIndexException {

        if (head==null||n<0){
            throw new InvalidIndexException();
        }
        else if (n==0){
            return head.element;
        }
        else{
            return getFromFront(head.next,n-1);
        }
    }

    @Override
    public Object getFromBack(ListNode head, int n) throws InvalidIndexException {
        int index = size(head)-n-1;
        if (head==null||index<0){
            throw new InvalidIndexException();
        }
        else if (index==0){
            return head.element;
        }
        else{
            return getFromFront(head.next,index-1);
        }
    }

    @Override
    public boolean deepEquals(ListNode head1, ListNode head2) {

        if(head1==null&&head2==null){
            return true;
        }
        else if(head1==null||head2==null){
            return false;
        }
        else if (head1.element!=head2.element){
            return false;
        }
        else{
            return deepEquals(head1.next,head2.next);
        }
    }

    @Override
    public ListNode deepCopy(ListNode head) {
        if(head==null){
            return null;
        }
        ListNode newNode = new ListNode(head.element, head.next);
        newNode.next = deepCopy(newNode.next);
        return newNode;
    }

    @Override
    public boolean containsDuplicates(ListNode head) {
        if(head==null){
            return false;
        }
        else{
            if (contains(head.next,head.element)){
                return true;
            }
            else{
                return containsDuplicates(head.next);
            }
        }
    }

    @Override
    public ListNode append(ListNode head1, ListNode head2) {
        if (head1==null){
            return head2;
        }
        if (head1.next==null){
            head1.next=head2;
            return head1;
        }
        else{
            head1.next=append(head1.next,head2);
        }
        return head1;

        /*if (head1==null){
            return head2;
        }
        ListNode current = head1.next;
        if (current==null){
            head1.next=head2;
            return head1;
        }
        if (current.next==null){
            current.next=head2;
            return head1;
        }
        else{
            return append(current.next,head2);
        }*/
    }

    @Override
    public ListNode flatten(ListNode head) {
        if (head==null){
            return null;
        }
        if(head.element instanceof ListNode){
            ListNode temp = head.next;
            head.next = ((ListNode) head.element).next;
            head.element = ((ListNode) head.element).element;
            head=append(head, temp);

        }
        head.next = flatten(head.next);
        return head;
    }

    @Override
    public boolean isCircular(ListNode head) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean containsCycles(ListNode head) {
        return false;
    }

    @Override
    public ListNode sort(ListNode head, Comparator comparator) {

            int size = size(head);
            if (size<=1){
                return head;
            }
            else{
                /*ListNode list2 = get(head,size/2);
                ListNode list1 = get(head,(size/2)-1);*/
                ListNode split = head;
                int index = 0;
                while(index<(size/2)-1){
                    split=split.next;
                    index++;
                }
                ListNode list2 = split.next;
                split.next = null;
                ListNode list1 = head;
                list1 = sort(list1, comparator);
                list2 = sort(list2, comparator);
                ListNode joined= new ListNode(1);
                ListNode current = joined;
                while(list1!=null&&list2!=null){
                    if (comparator.compare(list1.element, list2.element)<0){
                        current.next=list1;
                        list1=list1.next;
                    }
                    else{
                        current.next=list2;
                        list2=list2.next;
                    }
                    current= current.next;
                }
                if (list1==null){
                    current.next=list2;
                }
                else{
                    current.next = list1;
                }
                return joined.next;
                //return merge(list1, list2, comparator);
            }

    }

    /*private ListNode get(ListNode head, int n){

        if (head==null||n<0){
            return null;
        }
        else if (n==0){
            return head;
        }
        else{
            return get(head.next,n-1);
        }
    }*/

    /*private ListNode merge(ListNode list1, ListNode list2, Comparator comparator){
        ListNode joined= new ListNode(1);
        ListNode current = joined;
        while(list1!=null&&list2!=null){
            if (comparator.compare(list1.element, list2.element)<0){
                current.next=list1;
                list1=list1.next;
            }
            else{
                current.next=list2;
                list2=list2.next;
            }
            current= current.next;
        }
        if (list1==null){
            current.next=list2;
        }
        else{
            current.next = list1;
        }
        return joined.next;

    }*/

    @Override
    public ListNode map(ListNode head, ITransformation transformation) {
        if(head==null){
            return null;
        }
        head.element = transformation.transform(head.element);
        head.next=map(head.next,transformation);
        return head;
    }

    @Override
    public Object reduce(ListNode head, IOperator operator, Object initial) {
        if(head==null){
            return initial;
        }
        else{
            return reduce(head.next,operator,operator.operate(head.element,initial));
        }
    }

}
