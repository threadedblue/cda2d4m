/**
 */
package org.eclipse.emfcloud.jackson.junit.annotations;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test G</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.annotations.TestG#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage#getTestG()
 * @model annotation="JsonType property='@type'"
 * @generated
 */
public interface TestG extends EObject {
   /**
    * Returns the value of the '<em><b>Value</b></em>' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Value</em>' attribute.
    * @see #setValue(String)
    * @see org.eclipse.emfcloud.jackson.junit.annotations.AnnotationsPackage#getTestG_Value()
    * @model unique="false"
    * @generated
    */
   String getValue();

   /**
    * Sets the value of the '{@link org.eclipse.emfcloud.jackson.junit.annotations.TestG#getValue <em>Value</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param value the new value of the '<em>Value</em>' attribute.
    * @see #getValue()
    * @generated
    */
   void setValue(String value);

} // TestG
