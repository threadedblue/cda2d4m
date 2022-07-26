/**
 */
package org.eclipse.emfcloud.jackson.junit.model.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emfcloud.jackson.junit.model.ConcreteTypeTwo;
import org.eclipse.emfcloud.jackson.junit.model.ModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Concrete Type Two</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.model.impl.ConcreteTypeTwoImpl#getPropTypeTwo <em>Prop Type Two</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConcreteTypeTwoImpl extends AbstractTypeImpl implements ConcreteTypeTwo {
   /**
    * The default value of the '{@link #getPropTypeTwo() <em>Prop Type Two</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getPropTypeTwo()
    * @generated
    * @ordered
    */
   protected static final String PROP_TYPE_TWO_EDEFAULT = null;

   /**
    * The cached value of the '{@link #getPropTypeTwo() <em>Prop Type Two</em>}' attribute.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getPropTypeTwo()
    * @generated
    * @ordered
    */
   protected String propTypeTwo = PROP_TYPE_TWO_EDEFAULT;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected ConcreteTypeTwoImpl() {
      super();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   protected EClass eStaticClass() {
      return ModelPackage.Literals.CONCRETE_TYPE_TWO;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public String getPropTypeTwo() {
      return propTypeTwo;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void setPropTypeTwo(String newPropTypeTwo) {
      String oldPropTypeTwo = propTypeTwo;
      propTypeTwo = newPropTypeTwo;
      if (eNotificationRequired())
         eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.CONCRETE_TYPE_TWO__PROP_TYPE_TWO, oldPropTypeTwo, propTypeTwo));
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Object eGet(int featureID, boolean resolve, boolean coreType) {
      switch (featureID) {
         case ModelPackage.CONCRETE_TYPE_TWO__PROP_TYPE_TWO:
            return getPropTypeTwo();
      }
      return super.eGet(featureID, resolve, coreType);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void eSet(int featureID, Object newValue) {
      switch (featureID) {
         case ModelPackage.CONCRETE_TYPE_TWO__PROP_TYPE_TWO:
            setPropTypeTwo((String)newValue);
            return;
      }
      super.eSet(featureID, newValue);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public void eUnset(int featureID) {
      switch (featureID) {
         case ModelPackage.CONCRETE_TYPE_TWO__PROP_TYPE_TWO:
            setPropTypeTwo(PROP_TYPE_TWO_EDEFAULT);
            return;
      }
      super.eUnset(featureID);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public boolean eIsSet(int featureID) {
      switch (featureID) {
         case ModelPackage.CONCRETE_TYPE_TWO__PROP_TYPE_TWO:
            return PROP_TYPE_TWO_EDEFAULT == null ? propTypeTwo != null : !PROP_TYPE_TWO_EDEFAULT.equals(propTypeTwo);
      }
      return super.eIsSet(featureID);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public String toString() {
      if (eIsProxy()) return super.toString();

      StringBuilder result = new StringBuilder(super.toString());
      result.append(" (propTypeTwo: ");
      result.append(propTypeTwo);
      result.append(')');
      return result.toString();
   }

} //ConcreteTypeTwoImpl
