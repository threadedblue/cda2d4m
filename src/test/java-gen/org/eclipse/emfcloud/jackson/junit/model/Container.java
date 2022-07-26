/**
 */
package org.eclipse.emfcloud.jackson.junit.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.Container#getElements <em>Elements</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getContainer()
 * @model
 * @generated
 */
public interface Container extends EObject {
   /**
    * Returns the value of the '<em><b>Elements</b></em>' containment reference list.
    * The list contents are of type {@link org.eclipse.emfcloud.jackson.junit.model.AbstractType}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Elements</em>' containment reference list.
    * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage#getContainer_Elements()
    * @model containment="true" resolveProxies="true"
    * @generated
    */
   EList<AbstractType> getElements();

} // Container
