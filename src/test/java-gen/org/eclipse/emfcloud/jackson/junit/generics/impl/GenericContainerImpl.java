/**
 */
package org.eclipse.emfcloud.jackson.junit.generics.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emfcloud.jackson.junit.generics.GenericContainer;
import org.eclipse.emfcloud.jackson.junit.generics.GenericType;
import org.eclipse.emfcloud.jackson.junit.generics.GenericsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Generic Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.jackson.junit.generics.impl.GenericContainerImpl#getValues <em>Values</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenericContainerImpl extends MinimalEObjectImpl implements GenericContainer {
   /**
    * The cached value of the '{@link #getValues() <em>Values</em>}' containment reference list.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @see #getValues()
    * @generated
    * @ordered
    */
   protected EList<GenericType<?>> values;

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected GenericContainerImpl() {
      super();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   protected EClass eStaticClass() {
      return GenericsPackage.Literals.GENERIC_CONTAINER;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public EList<GenericType<?>> getValues() {
      if (values == null) {
         values = new EObjectContainmentEList.Resolving<GenericType<?>>(GenericType.class, this, GenericsPackage.GENERIC_CONTAINER__VALUES);
      }
      return values;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
      switch (featureID) {
         case GenericsPackage.GENERIC_CONTAINER__VALUES:
            return ((InternalEList<?>)getValues()).basicRemove(otherEnd, msgs);
      }
      return super.eInverseRemove(otherEnd, featureID, msgs);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Object eGet(int featureID, boolean resolve, boolean coreType) {
      switch (featureID) {
         case GenericsPackage.GENERIC_CONTAINER__VALUES:
            return getValues();
      }
      return super.eGet(featureID, resolve, coreType);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @SuppressWarnings("unchecked")
   @Override
   public void eSet(int featureID, Object newValue) {
      switch (featureID) {
         case GenericsPackage.GENERIC_CONTAINER__VALUES:
            getValues().clear();
            getValues().addAll((Collection<? extends GenericType<?>>)newValue);
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
         case GenericsPackage.GENERIC_CONTAINER__VALUES:
            getValues().clear();
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
         case GenericsPackage.GENERIC_CONTAINER__VALUES:
            return values != null && !values.isEmpty();
      }
      return super.eIsSet(featureID);
   }

} //GenericContainerImpl
