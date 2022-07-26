/**
 */
package org.eclipse.emfcloud.jackson.junit.model.util;

import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emfcloud.jackson.junit.model.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.emfcloud.jackson.junit.model.ModelPackage
 * @generated
 */
public class ModelAdapterFactory extends AdapterFactoryImpl {
   /**
    * The cached model package.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected static ModelPackage modelPackage;

   /**
    * Creates an instance of the adapter factory.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public ModelAdapterFactory() {
      if (modelPackage == null) {
         modelPackage = ModelPackage.eINSTANCE;
      }
   }

   /**
    * Returns whether this factory is applicable for the type of the object.
    * <!-- begin-user-doc -->
    * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
    * <!-- end-user-doc -->
    * @return whether this factory is applicable for the type of the object.
    * @generated
    */
   @Override
   public boolean isFactoryForType(Object object) {
      if (object == modelPackage) {
         return true;
      }
      if (object instanceof EObject) {
         return ((EObject)object).eClass().getEPackage() == modelPackage;
      }
      return false;
   }

   /**
    * The switch that delegates to the <code>createXXX</code> methods.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected ModelSwitch<Adapter> modelSwitch =
      new ModelSwitch<Adapter>() {
         @Override
         public Adapter caseUser(User object) {
            return createUserAdapter();
         }
         @Override
         public Adapter caseAddress(Address object) {
            return createAddressAdapter();
         }
         @Override
         public Adapter caseETypes(ETypes object) {
            return createETypesAdapter();
         }
         @Override
         public Adapter caseStringMap(Map.Entry<String, Value> object) {
            return createStringMapAdapter();
         }
         @Override
         public Adapter caseTMap(Map.Entry<Type, Value> object) {
            return createTMapAdapter();
         }
         @Override
         public Adapter caseTMapRef(Map.Entry<PrimaryObject, TargetObject> object) {
            return createTMapRefAdapter();
         }
         @Override
         public Adapter caseType(Type object) {
            return createTypeAdapter();
         }
         @Override
         public Adapter caseValue(Value object) {
            return createValueAdapter();
         }
         @Override
         public Adapter caseDataTypeMap(Map.Entry<String, String> object) {
            return createDataTypeMapAdapter();
         }
         @Override
         public Adapter casePrimaryObject(PrimaryObject object) {
            return createPrimaryObjectAdapter();
         }
         @Override
         public Adapter caseContainer(Container object) {
            return createContainerAdapter();
         }
         @Override
         public Adapter caseTargetObject(TargetObject object) {
            return createTargetObjectAdapter();
         }
         @Override
         public Adapter caseAbstractType(AbstractType object) {
            return createAbstractTypeAdapter();
         }
         @Override
         public Adapter caseConcreteTypeOne(ConcreteTypeOne object) {
            return createConcreteTypeOneAdapter();
         }
         @Override
         public Adapter caseConcreteTypeTwo(ConcreteTypeTwo object) {
            return createConcreteTypeTwoAdapter();
         }
         @Override
         public Adapter caseNode(Node object) {
            return createNodeAdapter();
         }
         @Override
         public Adapter caseAbstractNode(AbstractNode object) {
            return createAbstractNodeAdapter();
         }
         @Override
         public Adapter casePhysicalNode(PhysicalNode object) {
            return createPhysicalNodeAdapter();
         }
         @Override
         public Adapter caseVirtualNode(VirtualNode object) {
            return createVirtualNodeAdapter();
         }
         @Override
         public Adapter defaultCase(EObject object) {
            return createEObjectAdapter();
         }
      };

   /**
    * Creates an adapter for the <code>target</code>.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @param target the object to adapt.
    * @return the adapter for the <code>target</code>.
    * @generated
    */
   @Override
   public Adapter createAdapter(Notifier target) {
      return modelSwitch.doSwitch((EObject)target);
   }


   /**
    * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.model.User <em>User</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    * @return the new adapter.
    * @see org.eclipse.emfcloud.jackson.junit.model.User
    * @generated
    */
   public Adapter createUserAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.model.Address <em>Address</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    * @return the new adapter.
    * @see org.eclipse.emfcloud.jackson.junit.model.Address
    * @generated
    */
   public Adapter createAddressAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.model.ETypes <em>ETypes</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    * @return the new adapter.
    * @see org.eclipse.emfcloud.jackson.junit.model.ETypes
    * @generated
    */
   public Adapter createETypesAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>String Map</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    * @return the new adapter.
    * @see java.util.Map.Entry
    * @generated
    */
   public Adapter createStringMapAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>TMap</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    * @return the new adapter.
    * @see java.util.Map.Entry
    * @generated
    */
   public Adapter createTMapAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>TMap Ref</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    * @return the new adapter.
    * @see java.util.Map.Entry
    * @generated
    */
   public Adapter createTMapRefAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.model.Type <em>Type</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    * @return the new adapter.
    * @see org.eclipse.emfcloud.jackson.junit.model.Type
    * @generated
    */
   public Adapter createTypeAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.model.Value <em>Value</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    * @return the new adapter.
    * @see org.eclipse.emfcloud.jackson.junit.model.Value
    * @generated
    */
   public Adapter createValueAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>Data Type Map</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    * @return the new adapter.
    * @see java.util.Map.Entry
    * @generated
    */
   public Adapter createDataTypeMapAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.model.PrimaryObject <em>Primary Object</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    * @return the new adapter.
    * @see org.eclipse.emfcloud.jackson.junit.model.PrimaryObject
    * @generated
    */
   public Adapter createPrimaryObjectAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.model.Container <em>Container</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    * @return the new adapter.
    * @see org.eclipse.emfcloud.jackson.junit.model.Container
    * @generated
    */
   public Adapter createContainerAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.model.TargetObject <em>Target Object</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    * @return the new adapter.
    * @see org.eclipse.emfcloud.jackson.junit.model.TargetObject
    * @generated
    */
   public Adapter createTargetObjectAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.model.AbstractType <em>Abstract Type</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    * @return the new adapter.
    * @see org.eclipse.emfcloud.jackson.junit.model.AbstractType
    * @generated
    */
   public Adapter createAbstractTypeAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.model.ConcreteTypeOne <em>Concrete Type One</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    * @return the new adapter.
    * @see org.eclipse.emfcloud.jackson.junit.model.ConcreteTypeOne
    * @generated
    */
   public Adapter createConcreteTypeOneAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.model.ConcreteTypeTwo <em>Concrete Type Two</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    * @return the new adapter.
    * @see org.eclipse.emfcloud.jackson.junit.model.ConcreteTypeTwo
    * @generated
    */
   public Adapter createConcreteTypeTwoAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.model.Node <em>Node</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    * @return the new adapter.
    * @see org.eclipse.emfcloud.jackson.junit.model.Node
    * @generated
    */
   public Adapter createNodeAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.model.AbstractNode <em>Abstract Node</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    * @return the new adapter.
    * @see org.eclipse.emfcloud.jackson.junit.model.AbstractNode
    * @generated
    */
   public Adapter createAbstractNodeAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.model.PhysicalNode <em>Physical Node</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    * @return the new adapter.
    * @see org.eclipse.emfcloud.jackson.junit.model.PhysicalNode
    * @generated
    */
   public Adapter createPhysicalNodeAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for an object of class '{@link org.eclipse.emfcloud.jackson.junit.model.VirtualNode <em>Virtual Node</em>}'.
    * <!-- begin-user-doc -->
    * This default implementation returns null so that we can easily ignore cases;
    * it's useful to ignore a case when inheritance will catch all the cases anyway.
    * <!-- end-user-doc -->
    * @return the new adapter.
    * @see org.eclipse.emfcloud.jackson.junit.model.VirtualNode
    * @generated
    */
   public Adapter createVirtualNodeAdapter() {
      return null;
   }

   /**
    * Creates a new adapter for the default case.
    * <!-- begin-user-doc -->
    * This default implementation returns null.
    * <!-- end-user-doc -->
    * @return the new adapter.
    * @generated
    */
   public Adapter createEObjectAdapter() {
      return null;
   }

} //ModelAdapterFactory
