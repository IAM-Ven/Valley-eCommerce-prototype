package com.piper.valley.models.service;

import com.piper.valley.forms.AddStoreForm;
import com.piper.valley.forms.AddStoreProductForm;
import com.piper.valley.models.domain.Store;
import com.piper.valley.models.domain.StoreProduct;
import com.piper.valley.models.domain.User;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;


public interface StoreService {
	Optional<Store> getStoreById(Long id);

	Store acceptStore(Long storeId);

	Collection<Store> getAllStores();

	Collection<Store> getAllAppliedStores();

	Collection<Store> getAllAcceptedUserStores(Long storeOwnerId);

	Collection<Store> getAllPendingUserStores(Long storeOwnerId);

	Collection<Store> getAllNotAcceptedUserStores(Long storeOwnerId);

	Store add(AddStoreForm form, User user);

	StoreProduct addProductToStore(AddStoreProductForm form, User user) throws IOException;
}
