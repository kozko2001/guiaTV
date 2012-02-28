//
//  Model.m
//  GuiaTv
//
//  Created by Jordi Coscolla on 21/02/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import "Model.h"
#import "ChannelParser.h"
@implementation Model

@synthesize channels;

- (Model*) initFromServer
{
    self = [self init];
    
    ChannelParser *parser = [[ChannelParser alloc] init];
    channels = [parser start];
    
    return self;
}


@end
