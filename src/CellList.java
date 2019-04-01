// -----------------------------------------------------
// Assignment 4
// Part: 2
// Written by: Laurent (40020483)
// This assignment is meant practice the use of LinkedList
// Read through a list of cellphone information and create
// a LinkedList with that information
// -----------------------------------------------------


import javafx.scene.control.Cell;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Names and ID: Laurent Lao (40020483)
 * COMP249
 * Assignment #4 Part 2
 * Due Date: April 8 2019
 * CellList Class
 */

public class CellList {

	/**
	 * inner CellNode Class
	 */
	private class CellNode{

		CellPhone cellphone;
		CellNode nextNode;

		/**
		 * Default constructor
		 */
		public CellNode(){
			cellphone = null;
			nextNode = null;
		}

		/**
		 * Parameterized constructor
		 * @param cellphone object
		 * @param cellNode object
		 */
		public CellNode(CellPhone cellphone, CellNode cellNode)
		{
			this.cellphone = cellphone;
			this.nextNode = cellNode;
		}

		/**
		 * Copy Constructor
		 * @param cellNode object to be copied
		 */
		public CellNode(CellNode cellNode){
			this.cellphone = cellNode.cellphone;
			this.nextNode = cellNode.nextNode;
		}

		/**
		 * Clone method
		 * @return cloned CellNode
		 */
		public CellNode clone()
		{
			return new CellNode(this.cellphone, this.nextNode);
		}
	}

	private CellNode head;
	private int size = 0;

	public CellList()
	{
		head = null;
	}

	public void addToStart(CellPhone cellPhone)
	{
		// Add the new node to the head, assign previous head as the nextNode
		head = new CellNode(cellPhone, head);
		size++;

	}

	public void insertAtIndex(CellPhone cellPhone, int index)
	{
		if (size == 0)
		{
			addToStart(cellPhone);
		}
		else
		{
			// Get related nodes
			CellNode nodeBeforeIndex = nodeAtIndex(index - 1, index);
			CellNode nodeAtIndex = nodeBeforeIndex.nextNode;

			// Create new node with nodeAtIndex as its nextNode
			CellNode nodeToInsert = new CellNode(cellPhone, nodeAtIndex);
			size++;

			// Point previous node to new node
			nodeBeforeIndex.nextNode = nodeToInsert;

			// Prevent leaks
			nodeBeforeIndex = null;
			nodeAtIndex = null;
			nodeToInsert = null;

			return;
		}
	}

	public void deleteFromIndex(int index)
	{
		try
		{
			// Checks if there are any elements to delete
			if (size == 0)
			{
				throw  new NullPointerException();
			}

			// Get the node right before the one at the index
			CellNode nodeBeforeIndex = nodeAtIndex(index - 1, index);

			// Skip the next node and go to the one after; assign it to that previous node
			nodeBeforeIndex.nextNode = nodeBeforeIndex.nextNode.nextNode;
			size--;

			// Preventing privacy leaks
			nodeBeforeIndex = null;
			return;

		}
		catch (NullPointerException e)
		{
			System.out.println("Error: the list is of size 0, can't delete element.");
			System.exit(0);
		}
	}

	public void deleteFromStart()
	{
		if (size > 1)
		{
			head = head.nextNode;
			size--;
		}
		else if (size == 1)
		{
			head = null;
			size = 1;
		}
		else
		{
			System.out.println("No more elements to delete");
		}
	}

	public CellNode find(long serialNumber)
	{
		int searchCounter = 1;
		CellNode nodeToCheck = head;

		while (nodeToCheck != null)
		{
			System.out.println("Search iteration #" + searchCounter);
			// Check if it is equal, return the nodeToCheck if true
			if (nodeToCheck.cellphone.getSerialNumber() == serialNumber)
			{
				return nodeToCheck;
			}
			else
			{
				// Go to next node
				nodeToCheck = nodeToCheck.nextNode;
				searchCounter++;
			}
		}

		// If it hits a null node (the end)
		return null;
	}

	public void searchFor(long serialNumber)
	{
		System.out.println("\nSearching for SN# " + serialNumber + " in list.");
		CellNode node = find(serialNumber);

		if(node != null)
		{
			System.out.println("Found matching serial number.\nHere's the information of the cellphone:");
			System.out.println("\n" + node.cellphone);
		}
		else
		{
			System.out.println("No matching serial number found.\n");
		}
	}

	public boolean contains(long serialNumber)
	{
		if (find(serialNumber) == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public void showContents()
	{
		if (head != null)
		{
			int nodeCounter = 0;
			CellNode currentNode = head;
			while (currentNode != null)
			{
				System.out.println("Node " + nodeCounter);
				System.out.println(currentNode.cellphone);

				// Go to next node
				currentNode = currentNode.nextNode;
				nodeCounter++;
			}
		}
		else
		{
			System.out.println("No nodes to print.");
		}
	}

	public CellNode nodeAtIndex(int indexToCheck, int indexParamOfCallingMethod)
	{
		try
		{
			// Check if it's a valid index
			if ((indexToCheck < 0 && indexToCheck >= size) || (indexParamOfCallingMethod < 0 && indexParamOfCallingMethod >= size))
			{
				throw new NoSuchElementException();
			}
			else
			{
				// Return the node at the specified index
				CellNode node = head;
				for (int i = 0; i < indexToCheck; i++)
				{
					if (node != null)
					{
						node = node.nextNode;
					}
					else
					{
						throw new NullPointerException();
					}
				}

				return node;
			}
		}
		catch (NoSuchElementException e)
		{
			System.out.println("Error: " + indexParamOfCallingMethod + " is invalid." + e.getMessage());
			System.exit(0);
		}
		catch (NullPointerException e)
		{
			System.out.println("Error: unexpected null node in the middle of the linked list.");
			System.exit(0);
		}

		// Keeps compiler happy
		return null;
	}

	public void add(CellPhone cellPhone)
	{
		if (size != 0)
		{
			// Adding cellPhone at the end
			System.out.println("Current size: " + size);
			System.out.println("Adding CellPhone (SN# " + cellPhone.getSerialNumber() + ") to list");
			CellNode lastNode = nodeAtIndex(size - 1 , size - 1 );
			lastNode.nextNode = new CellNode(cellPhone, null);
			size++;
			System.out.println("The list is now size " + size);
		}
		else
		{
			addToStart(cellPhone);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}
		CellList cellList = (CellList) o;
		return size == cellList.size && head.equals(cellList.head) &&
				checkIfSameElements(cellList.head);
	}

	public boolean checkIfSameElements(CellNode node)
	{
		CellNode list1Node = head.nextNode;
		CellNode list2Node = node.nextNode;

		for (int i = 1; i < size; i++)
		{
			if (list1Node == null || list2Node == null)
			{
				// Break and return true
				break;
			}
			else if (!list1Node.equals(list2Node))
			{
				return false;
			}
			else
			{
				// Go to next node and check again
				list1Node = list1Node.nextNode;
				list2Node = list2Node.nextNode;
			}
		}

		return true;
	}
}
